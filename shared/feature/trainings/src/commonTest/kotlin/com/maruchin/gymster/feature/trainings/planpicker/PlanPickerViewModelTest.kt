package com.maruchin.gymster.feature.trainings.planpicker

import app.cash.turbine.test
import com.maruchin.gymster.core.clock.FakeClock
import com.maruchin.gymster.core.clock.di.coreClockTestModule
import com.maruchin.gymster.data.plans.di.dataPlansTestModule
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.plans.repository.FakePlansRepository
import com.maruchin.gymster.data.trainings.di.dataTrainingsTestModule
import com.maruchin.gymster.data.trainings.repository.FakeTrainingsRepository
import com.maruchin.gymster.feature.trainings.di.featureTrainingsModule
import io.kotest.matchers.collections.shouldBeEmpty
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
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class PlanPickerViewModelTest : KoinTest {

    private val plansRepository: FakePlansRepository by inject()
    private val trainingsRepository: FakeTrainingsRepository by inject()
    private val clock: FakeClock by inject()
    private val viewModel: PlanPickerViewModel by inject()

    private val today = LocalDate(year = 2024, monthNumber = 8, dayOfMonth = 2)
    private val yesterday = LocalDate(year = 2024, monthNumber = 8, dayOfMonth = 1)
    private val yesterdayMillis = yesterday.atTime(hour = 12, minute = 0)
        .toInstant(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                featureTrainingsModule,
                dataTrainingsTestModule,
                dataPlansTestModule,
                coreClockTestModule
            )
        }
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `emit loaded state when plans available`() = runTest {
        clock.setNow(today)
        plansRepository.setPlans(samplePlans)

        viewModel.uiState.test {
            awaitItem() shouldBe PlanPickerUiState.Loading

            awaitItem() shouldBe PlanPickerUiState.Loaded(
                plans = samplePlans,
                selectedPlan = null,
                selectedDay = null,
                selectedDate = today
            )
        }
    }

    @Test
    fun `emit new loaded state when plan selected`() = runTest {
        clock.setNow(today)
        plansRepository.setPlans(samplePlans)
        val selectedPlan = samplePlans.first()

        viewModel.uiState.test {
            skipItems(count = 2)
            viewModel.selectPlan(selectedPlan)

            awaitItem() shouldBe PlanPickerUiState.Loaded(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedDay = null,
                selectedDate = today
            )
        }
    }

    @Test
    fun `emit new loaded state when day selected`() = runTest {
        clock.setNow(today)
        plansRepository.setPlans(samplePlans)
        val selectedPlan = samplePlans.first()
        val selectedDay = selectedPlan.days.first()

        viewModel.uiState.test {
            skipItems(count = 2)
            viewModel.selectPlan(selectedPlan)
            skipItems(count = 1)
            viewModel.selectDay(selectedDay)

            awaitItem() shouldBe PlanPickerUiState.Loaded(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedDay = selectedDay,
                selectedDate = today
            )
        }
    }

    @Test
    fun `emit new loaded state when date selected`() = runTest {
        clock.setNow(today)
        plansRepository.setPlans(samplePlans)
        val selectedPlan = samplePlans.first()
        val selectedDay = selectedPlan.days.first()

        viewModel.uiState.test {
            skipItems(count = 2)
            viewModel.selectPlan(selectedPlan)
            skipItems(count = 1)
            viewModel.selectDay(selectedDay)
            skipItems(count = 1)
            viewModel.selectDate(yesterdayMillis)

            awaitItem() shouldBe PlanPickerUiState.Loaded(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedDay = selectedDay,
                selectedDate = yesterday
            )
        }
    }

    @Test
    fun `start training when all data selected`() = runTest {
        clock.setNow(today)
        plansRepository.setPlans(samplePlans)
        val selectedPlan = samplePlans.first()
        val selectedDay = selectedPlan.days.first()

        viewModel.selectPlan(selectedPlan)
        viewModel.selectDay(selectedDay)
        viewModel.selectDate(yesterdayMillis)
        trainingsRepository.observeAllTrainings().test {
            awaitItem().shouldBeEmpty()

            viewModel.startTraining()

            awaitItem().let { trainings ->
                trainings shouldHaveSize 1
                trainings.first().let { training ->
                    training.date shouldBe yesterday
                    training.planName shouldBe selectedPlan.name
                    training.name shouldBe selectedDay.name
                }
            }
        }
    }
}
