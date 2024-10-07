package com.maruchin.gymster.shared.feature.trainings2.trainingeditor

import app.cash.turbine.test
import com.maruchin.gymster.data.trainings2.di.dataTrainings2TestModule
import com.maruchin.gymster.data.trainings2.model.samplePullTraining
import com.maruchin.gymster.data.trainings2.model.sampleTrainings
import com.maruchin.gymster.data.trainings2.repository.FakeTrainingsRepository
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

    private val trainingsRepository: FakeTrainingsRepository by inject()
    private val viewModel by lazy { TrainingEditorViewModel(samplePullTraining.id) }

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin { modules(dataTrainings2TestModule) }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `emit selected training`() = runTest {
        trainingsRepository.setTrainings(sampleTrainings)

        viewModel.uiState.test {
            awaitItem() shouldBe TrainingEditorUiState()
            awaitItem() shouldBe TrainingEditorUiState(samplePullTraining)
        }
    }

    @Test
    fun `update weight`() = runTest {
        trainingsRepository.setTrainings(sampleTrainings)
        val setResult = samplePullTraining.exercises.first().results.first()

        viewModel.uiState.test {
            awaitItem()
            awaitItem().training?.getSetResult(setResult.id)?.weight shouldBe null

            viewModel.updateWeight(setResultId = setResult.id, weight = 70.0)
            awaitItem().training?.getSetResult(setResult.id)?.weight shouldBe 70.0
        }
    }

    @Test
    fun `update reps`() = runTest {
        trainingsRepository.setTrainings(sampleTrainings)
        val setResult = samplePullTraining.exercises.first().results.first()

        viewModel.uiState.test {
            awaitItem()
            awaitItem().training?.getSetResult(setResult.id)?.reps shouldBe null

            viewModel.updateReps(setResultId = setResult.id, reps = 5)
            awaitItem().training?.getSetResult(setResult.id)?.reps shouldBe 5
        }
    }
}
