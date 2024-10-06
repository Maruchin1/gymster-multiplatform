package com.maruchin.gymster.feature.trainings.starttrainingblock

import app.cash.turbine.test
import com.maruchin.gymster.data.plans.di.dataPlansTestModule
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.plans.repository.FakePlansRepository
import com.maruchin.gymster.data.trainings.di.dataTrainingsTestModule
import com.maruchin.gymster.data.trainings.repository.FakeTrainingsRepository
import io.kotest.matchers.collections.shouldHaveSize
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
import kotlinx.datetime.LocalDate
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class StartTrainingBlockViewModelTest : KoinTest {

    private val plansRepository: FakePlansRepository by inject()
    private val trainingsRepository: FakeTrainingsRepository by inject()
    private val viewModel by lazy { StartTrainingBlockViewModel() }

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin { modules(dataPlansTestModule, dataTrainingsTestModule) }
        trainingsRepository.setTrainingBlocks(emptyList())
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `should emit loaded state with plans`() = runTest {
        plansRepository.setPlans(samplePlans)

        viewModel.uiState.test {
            awaitItem() shouldBe StartTrainingBlockUiState()
            awaitItem() shouldBe StartTrainingBlockUiState(
                plans = samplePlans,
                selectedPlan = null,
                selectedStartDate = null,
                selectedWeeksDuration = null,
                isCreated = false
            )
        }
    }

    @Test
    fun `should create new training block`() = runTest {
        val plan = samplePlans.first()
        val startDate = LocalDate(year = 2024, monthNumber = 9, dayOfMonth = 9)
        val weekDuration = 8
        plansRepository.setPlans(samplePlans)
        trainingsRepository.setTrainingBlocks(emptyList())

        viewModel.uiState.test {
            skipItems(2)

            viewModel.selectPlan(plan)
            awaitItem() shouldBe StartTrainingBlockUiState(
                plans = samplePlans,
                selectedPlan = plan,
                selectedStartDate = null,
                selectedWeeksDuration = null,
                isCreated = false
            )

            viewModel.selectStarDate(startDate)
            awaitItem() shouldBe StartTrainingBlockUiState(
                plans = samplePlans,
                selectedPlan = plan,
                selectedStartDate = startDate,
                selectedWeeksDuration = null,
                isCreated = false
            )

            viewModel.selectWeeksDuration(weekDuration)
            awaitItem() shouldBe StartTrainingBlockUiState(
                plans = samplePlans,
                selectedPlan = plan,
                selectedStartDate = startDate,
                selectedWeeksDuration = weekDuration,
                isCreated = false
            )

            viewModel.startTrainingBlock()
            awaitItem() shouldBe StartTrainingBlockUiState(
                plans = samplePlans,
                selectedPlan = plan,
                selectedStartDate = startDate,
                selectedWeeksDuration = weekDuration,
                isCreated = true
            )
        }
        trainingsRepository.observeAllTrainingBlocks().test {
            awaitItem() shouldHaveSize 1
        }
    }
}
