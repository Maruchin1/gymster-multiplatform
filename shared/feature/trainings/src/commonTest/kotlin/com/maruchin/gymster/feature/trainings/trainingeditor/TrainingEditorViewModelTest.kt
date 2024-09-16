package com.maruchin.gymster.feature.trainings.trainingeditor

import app.cash.turbine.test
import com.maruchin.gymster.data.trainings.di.dataTrainingsTestModule
import com.maruchin.gymster.data.trainings.model.sampleTrainingBlocks
import com.maruchin.gymster.data.trainings.repository.FakeTrainingsRepository
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertIs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class TrainingEditorViewModelTest : KoinTest {

    private val trainingBlock = sampleTrainingBlocks.first()
    private val week = trainingBlock.weeks.first()
    private val training = week.trainings.first()
    private val exercise = training.exercises.first()
    private val trainingsRepository: FakeTrainingsRepository by inject()
    private val viewModel by lazy {
        TrainingEditorViewModel(trainingBlock.id, training.id, exercise.id)
    }

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin { modules(dataTrainingsTestModule) }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `emit loaded state when training available`() = runTest {
        trainingsRepository.setTrainingBlocks(sampleTrainingBlocks)

        viewModel.uiState.test {
            awaitItem() shouldBe TrainingEditorUiState.Loading

            awaitItem() shouldBe TrainingEditorUiState.Loaded(training, exercise.id)
        }
    }

    @Test
    fun `do not emit new state when training not available`() = runTest {
        trainingsRepository.setTrainingBlocks(emptyList())

        viewModel.uiState.test {
            awaitItem() shouldBe TrainingEditorUiState.Loading

            expectNoEvents()
        }
    }

    @Test
    fun `update weight`() = runTest {
        trainingsRepository.setTrainingBlocks(sampleTrainingBlocks)
        val setResult = trainingBlock.weeks.first().trainings.first().exercises[3].results.first()
        val updatedWeight = 7.5

        viewModel.uiState.test {
            skipItems(1)
            awaitItem().let {
                assertIs<TrainingEditorUiState.Loaded>(it)
                it.training.exercises[3].results.first().weight.shouldBeNull()
            }

            viewModel.updateWeight(setResult.id, updatedWeight)

            awaitItem().let {
                assertIs<TrainingEditorUiState.Loaded>(it)
                it.training.exercises[3].results.first().weight shouldBe updatedWeight
            }
        }
    }

    @Test
    fun `update reps`() = runTest {
        trainingsRepository.setTrainingBlocks(sampleTrainingBlocks)
        val setResult = trainingBlock.weeks.first().trainings.first().exercises[3].results.first()
        val updatedReps = 15

        viewModel.uiState.test {
            skipItems(1)
            awaitItem().let {
                assertIs<TrainingEditorUiState.Loaded>(it)
                it.training.exercises[3].results.first().reps.shouldBeNull()
            }

            viewModel.updateReps(setResult.id, updatedReps)

            awaitItem().let {
                assertIs<TrainingEditorUiState.Loaded>(it)
                it.training.exercises[3].results.first().reps shouldBe updatedReps
            }
        }
    }
}
