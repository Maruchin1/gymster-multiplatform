package com.maruchin.gymster.feature.trainings.progresseditor

import app.cash.turbine.test
import com.maruchin.gymster.data.trainings.di.dataTrainingsTestModule
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.sampleTrainingBlocks
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

    private val trainingBlock = sampleTrainingBlocks.first()
    private val week = trainingBlock.weeks.first()
    private val training = week.trainings.first()
    private val exercise = training.exercises.first()
    private val progressIndex = 0
    private val progress = exercise.progress.first()
    private val trainingsRepository: FakeTrainingsRepository by inject()
    private val viewModel: ProgressEditorViewModel by inject {
        parametersOf(trainingBlock.id, week.number, training.id, exercise.id, progressIndex)
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
        trainingsRepository.setTrainingBlocks(sampleTrainingBlocks)

        viewModel.uiState.test {
            awaitItem() shouldBe ProgressEditorUiState.Loading

            awaitItem() shouldBe ProgressEditorUiState.Loaded(exercise, progress)
        }
    }

    @Test
    fun `do not emit loaded state when progress not available`() = runTest {
        trainingsRepository.setTrainingBlocks(emptyList())

        viewModel.uiState.test {
            awaitItem() shouldBe ProgressEditorUiState.Loading

            expectNoEvents()
        }
    }

    @Test
    fun `save progress`() = runTest {
        trainingsRepository.setTrainingBlocks(sampleTrainingBlocks)
        val updatedProgress = Progress(weight = 100.0, reps = 10)

        trainingsRepository.observeTrainingBlock(trainingBlock.id).test {
            awaitItem()!!.getWeek(week.number)
                .getTraining(training.id)
                .getExercise(exercise.id)
                .getProgress(progressIndex) shouldBe progress

            viewModel.saveProgress(updatedProgress)

            awaitItem()!!.getWeek(week.number)
                .getTraining(training.id)
                .getExercise(exercise.id)
                .getProgress(progressIndex) shouldBe updatedProgress
        }
    }
}
