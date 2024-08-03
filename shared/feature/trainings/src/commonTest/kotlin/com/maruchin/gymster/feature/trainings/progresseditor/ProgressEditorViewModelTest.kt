package com.maruchin.gymster.feature.trainings.progresseditor

import app.cash.turbine.test
import com.maruchin.gymster.data.trainings.di.dataTrainingsTestModule
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.sampleTrainings
import com.maruchin.gymster.data.trainings.repository.FakeTrainingsRepository
import com.maruchin.gymster.feature.trainings.di.featureTrainingsModule
import io.kotest.matchers.shouldBe
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class ProgressEditorViewModelTest : KoinTest {

    private val training = sampleTrainings.first()
    private val exercise = training.exercises.first()
    private val progressIndex = 0
    private val progress = exercise.progress.first()
    private val trainingsRepository: FakeTrainingsRepository by inject()
    private val viewModel: ProgressEditorViewModel by inject {
        parametersOf(training.id, exercise.id, progressIndex)
    }

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin { modules(featureTrainingsModule, dataTrainingsTestModule) }
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun `emit loaded state when progress available`() = runTest {
        trainingsRepository.setTrainings(sampleTrainings)

        viewModel.uiState.test {
            awaitItem() shouldBe ProgressEditorUiState.Loading

            awaitItem() shouldBe ProgressEditorUiState.Loaded(progress)
        }
    }

    @Test
    fun `do not emit loaded state when progress not available`() = runTest {
        trainingsRepository.setTrainings(emptyList())

        viewModel.uiState.test {
            awaitItem() shouldBe ProgressEditorUiState.Loading

            expectNoEvents()
        }
    }

    @Test
    fun `save progress`() = runTest {
        trainingsRepository.setTrainings(sampleTrainings)
        val updatedProgress = Progress(weight = 100.0, reps = 10)

        trainingsRepository.observeTraining(training.id).test {
            awaitItem()!!.getExercise(exercise.id).getProgress(progressIndex) shouldBe progress

            viewModel.saveProgress(updatedProgress)

            awaitItem()!!.getExercise(exercise.id).getProgress(progressIndex) shouldBe
                updatedProgress
        }
    }
}
