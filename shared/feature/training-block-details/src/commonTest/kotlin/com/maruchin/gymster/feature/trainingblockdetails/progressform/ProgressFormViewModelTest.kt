package com.maruchin.gymster.feature.trainingblockdetails.progressform

import app.cash.turbine.test
import com.maruchin.gymster.data.trainings.di.dataTrainingsTestModule
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.sampleTrainingBlocks
import com.maruchin.gymster.data.trainings.repository.FakeTrainingsRepository
import com.maruchin.gymster.feature.trainingblockdetails.di.featureTrainingBlockDetailsModule
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
class ProgressFormViewModelTest : KoinTest {

    private val trainingBlock = sampleTrainingBlocks.first()
    private val week = trainingBlock.weeks.first()
    private val training = week.trainings.first()
    private val exercise = training.exercises.first()
    private val setProgress = exercise.progress.first()
    private val trainingsRepository: FakeTrainingsRepository by inject()
    private val viewModel: ProgressFormViewModel by inject {
        parametersOf(trainingBlock.id, exercise.id, setProgress.id)
    }

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin { modules(featureTrainingBlockDetailsModule, dataTrainingsTestModule) }
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
            awaitItem() shouldBe ProgressFormUiState.Loading

            awaitItem() shouldBe ProgressFormUiState.Loaded(exercise, setProgress)
        }
    }

    @Test
    fun `do not emit loaded state when progress not available`() = runTest {
        trainingsRepository.setTrainingBlocks(emptyList())

        viewModel.uiState.test {
            awaitItem() shouldBe ProgressFormUiState.Loading

            expectNoEvents()
        }
    }

    @Test
    fun `save progress`() = runTest {
        trainingsRepository.setTrainingBlocks(sampleTrainingBlocks)
        val updatedProgress = Progress(weight = 100.0, reps = 10)

        trainingsRepository.observeTrainingBlock(trainingBlock.id).test {
            awaitItem()!!.getSetProgress(setProgress.id).progress shouldBe setProgress.progress

            viewModel.saveProgress(updatedProgress)

            awaitItem()!!.getSetProgress(setProgress.id).progress shouldBe updatedProgress
        }
    }
}
