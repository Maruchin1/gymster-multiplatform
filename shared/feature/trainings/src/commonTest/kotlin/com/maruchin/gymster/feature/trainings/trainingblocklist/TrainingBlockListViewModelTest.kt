package com.maruchin.gymster.feature.trainings.trainingblocklist

import app.cash.turbine.test
import com.maruchin.gymster.data.plans.di.dataPlansTestModule
import com.maruchin.gymster.data.trainings.di.dataTrainingsTestModule
import com.maruchin.gymster.data.trainings.model.sampleTrainingBlocks
import com.maruchin.gymster.data.trainings.repository.FakeTrainingsRepository
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
class TrainingBlockListViewModelTest : KoinTest {

    private val trainingsRepository: FakeTrainingsRepository by inject()
    private val viewModel by lazy { TrainingBlockListViewModel() }

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin { modules(dataTrainingsTestModule, dataPlansTestModule) }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `emit loaded state with training blocks when available`() = runTest {
        trainingsRepository.setTrainingBlocks(sampleTrainingBlocks)

        viewModel.uiState.test {
            awaitItem() shouldBe TrainingBlockListUiState()
            awaitItem() shouldBe TrainingBlockListUiState(sampleTrainingBlocks)
        }
    }

    @Test
    fun `emit loaded state with empty list when training blocks not available`() = runTest {
        trainingsRepository.setTrainingBlocks(emptyList())

        viewModel.uiState.test {
            awaitItem() shouldBe TrainingBlockListUiState()
        }
    }
}
