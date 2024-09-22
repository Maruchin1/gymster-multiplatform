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
    private val trainingsRepository: FakeTrainingsRepository by inject()
    private val viewModel by lazy {
        TrainingEditorViewModel(
            trainingBlock.id,
            weekIndex = 0,
            trainingIndex = 0,
            initialExerciseIndex = 0
        )
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
            awaitItem() shouldBe TrainingEditorUiState()

            awaitItem() shouldBe TrainingEditorUiState(
                training = trainingBlock.weeks.first().trainings.first(),
                previousTraining = null,
                initialExerciseIndex = 0
            )
        }
    }

    @Test
    fun `do not emit new state when training not available`() = runTest {
        trainingsRepository.setTrainingBlocks(emptyList())

        viewModel.uiState.test {
            awaitItem() shouldBe TrainingEditorUiState()

            expectNoEvents()
        }
    }

    @Test
    fun `update weight`() = runTest {
        trainingsRepository.setTrainingBlocks(sampleTrainingBlocks)
        val updatedWeight = 7.5

        viewModel.uiState.test {
            skipItems(1)
            awaitItem().let {
                it.training!!.exercises[3].results.first().weight.shouldBeNull()
            }

            viewModel.updateWeight(exerciseIndex = 3, setIndex = 0, updatedWeight)

            awaitItem().let {
                it.training!!.exercises[3].results.first().weight shouldBe updatedWeight
            }
        }
    }

    @Test
    fun `update reps`() = runTest {
        trainingsRepository.setTrainingBlocks(sampleTrainingBlocks)
        val updatedReps = 15

        viewModel.uiState.test {
            skipItems(1)
            awaitItem().let {
                it.training!!.exercises[3].results.first().reps.shouldBeNull()
            }

            viewModel.updateReps(exerciseIndex = 3, setIndex = 0, updatedReps)

            awaitItem().let {
                it.training!!.exercises[3].results.first().reps shouldBe updatedReps
            }
        }
    }
}
