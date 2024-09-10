package com.maruchin.gymster.feature.trainings.trainingblocklist

import app.cash.turbine.test
import com.maruchin.gymster.data.plans.di.dataPlansTestModule
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.plans.repository.FakePlansRepository
import com.maruchin.gymster.data.trainings.di.dataTrainingsTestModule
import com.maruchin.gymster.data.trainings.model.sampleTrainingBlocks
import com.maruchin.gymster.data.trainings.repository.FakeTrainingsRepository
import com.maruchin.gymster.feature.trainings.di.featureTrainingsModule
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
import kotlinx.datetime.LocalDate
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class TrainingBlockListViewModelTest : KoinTest {

    private val trainingsRepository: FakeTrainingsRepository by inject()
    private val plansRepository: FakePlansRepository by inject()
    private val viewModel: TrainingBlockListViewModel by inject()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin { modules(featureTrainingsModule, dataTrainingsTestModule, dataPlansTestModule) }
        plansRepository.setPlans(samplePlans)
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
            awaitItem() shouldBe TrainingBlockListUiState.Loading
            awaitItem() shouldBe TrainingBlockListUiState.Loaded(sampleTrainingBlocks)
        }
    }

    @Test
    fun `emit loaded state with empty list when training blocks not available`() = runTest {
        trainingsRepository.setTrainingBlocks(emptyList())

        viewModel.uiState.test {
            awaitItem() shouldBe TrainingBlockListUiState.Loading
            awaitItem() shouldBe TrainingBlockListUiState.Loaded(emptyList())
        }
    }

    @Test
    fun `create training block`() = runTest {
        val plan = samplePlans.first()
        val startDate = LocalDate(year = 2024, monthNumber = 9, dayOfMonth = 9)
        val weekDuration = 8
        trainingsRepository.setTrainingBlocks(emptyList())

        viewModel.uiState.test {
            awaitItem() shouldBe TrainingBlockListUiState.Loading
            awaitItem() shouldBe TrainingBlockListUiState.Loaded(emptyList())

            viewModel.createTrainingBlock(
                planId = plan.id,
                startDate = startDate,
                weekDuration = weekDuration
            )

            awaitItem().let { state ->
                assertIs<TrainingBlockListUiState.Loaded>(state)
                state.trainingBlocks.first().let { trainingBlock ->
                    trainingBlock.planName shouldBe plan.name
                    trainingBlock.startDate shouldBe startDate
                    trainingBlock.weeks.size shouldBe weekDuration
                }
            }
        }
    }
}
