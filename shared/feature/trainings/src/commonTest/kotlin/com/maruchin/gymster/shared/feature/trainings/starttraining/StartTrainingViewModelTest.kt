package com.maruchin.gymster.shared.feature.trainings.starttraining

import app.cash.turbine.test
import com.maruchin.gymster.core.utils.FakeClock
import com.maruchin.gymster.core.utils.di.coreClockTestModule
import com.maruchin.gymster.data.plans.di.dataPlansTestModule
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.plans.repository.FakePlansRepository
import com.maruchin.gymster.data.trainings.di.dataTrainings2TestModule
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
class StartTrainingViewModelTest : KoinTest {

    private val plansRepository: FakePlansRepository by inject()
    private val trainingsRepository: FakeTrainingsRepository by inject()
    private val clock: FakeClock by inject()
    private val viewModel by lazy { StartTrainingViewModel() }

    private val currentDate = LocalDate(2024, 10, 10)

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin { modules(dataPlansTestModule, dataTrainings2TestModule, coreClockTestModule) }

        plansRepository.setPlans(samplePlans)
        clock.setNow(currentDate)
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `emit all plans`() = runTest {
        viewModel.uiState.test {
            awaitItem() shouldBe StartTrainingUiState(selectedDate = currentDate)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedDate = currentDate
            )
        }
    }

    @Test
    fun `select plan`() = runTest {
        val selectedPlan = samplePlans.first()

        viewModel.uiState.test {
            awaitItem() shouldBe StartTrainingUiState(selectedDate = currentDate)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedDate = currentDate
            )

            viewModel.selectPlan(selectedPlan)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedDate = currentDate
            )
        }
    }

    @Test
    fun `reset plan`() = runTest {
        val selectedPlan = samplePlans.first()

        viewModel.uiState.test {
            awaitItem() shouldBe StartTrainingUiState(selectedDate = currentDate)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedDate = currentDate
            )

            viewModel.selectPlan(selectedPlan)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedDate = currentDate
            )

            viewModel.resetPlan()
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedDate = currentDate
            )
        }
    }

    @Test
    fun `select training`() = runTest {
        val selectedPlan = samplePlans.first()
        val selectedTraining = selectedPlan.trainings.first()

        viewModel.uiState.test {
            awaitItem() shouldBe StartTrainingUiState(selectedDate = currentDate)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedDate = currentDate
            )

            viewModel.selectPlan(selectedPlan)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedDate = currentDate
            )

            viewModel.selectTraining(selectedTraining)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedTraining = selectedTraining,
                selectedDate = currentDate
            )
        }
    }

    @Test
    fun `reset training`() = runTest {
        val selectedPlan = samplePlans.first()
        val selectedTraining = selectedPlan.trainings.first()

        viewModel.uiState.test {
            awaitItem() shouldBe StartTrainingUiState(selectedDate = currentDate)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedDate = currentDate
            )

            viewModel.selectPlan(selectedPlan)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedDate = currentDate
            )

            viewModel.selectTraining(selectedTraining)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedTraining = selectedTraining,
                selectedDate = currentDate
            )

            viewModel.resetTraining()
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedDate = currentDate
            )
        }
    }

    @Test
    fun `select date`() = runTest {
        val selectedPlan = samplePlans.first()
        val selectedTraining = selectedPlan.trainings.first()
        val selectedDate = LocalDate(2024, 10, 9)

        viewModel.uiState.test {
            awaitItem() shouldBe StartTrainingUiState(selectedDate = currentDate)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedDate = currentDate
            )

            viewModel.selectPlan(selectedPlan)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedDate = currentDate
            )

            viewModel.selectTraining(selectedTraining)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedTraining = selectedTraining,
                selectedDate = currentDate
            )

            viewModel.selectDate(selectedDate)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedTraining = selectedTraining,
                selectedDate = selectedDate
            )
        }
    }

    @Test
    fun `reset date`() = runTest {
        val selectedPlan = samplePlans.first()
        val selectedTraining = selectedPlan.trainings.first()
        val selectedDate = LocalDate(2024, 10, 9)

        viewModel.uiState.test {
            awaitItem() shouldBe StartTrainingUiState(selectedDate = currentDate)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedDate = currentDate
            )

            viewModel.selectPlan(selectedPlan)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedDate = currentDate
            )

            viewModel.selectTraining(selectedTraining)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedTraining = selectedTraining,
                selectedDate = currentDate
            )

            viewModel.selectDate(selectedDate)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedTraining = selectedTraining,
                selectedDate = selectedDate
            )

            viewModel.resetDate()
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedTraining = selectedTraining
            )
        }
    }

    @Test
    fun `start training`() = runTest {
        val selectedPlan = samplePlans.first()
        val selectedTraining = selectedPlan.trainings.first()
        val selectedDate = LocalDate(2024, 10, 9)

        viewModel.uiState.test {
            awaitItem() shouldBe StartTrainingUiState(selectedDate = currentDate)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedDate = currentDate
            )

            viewModel.selectPlan(selectedPlan)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedDate = currentDate
            )

            viewModel.selectTraining(selectedTraining)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedTraining = selectedTraining,
                selectedDate = currentDate
            )

            viewModel.selectDate(selectedDate)
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedTraining = selectedTraining,
                selectedDate = selectedDate
            )

            viewModel.startTraining()
            awaitItem() shouldBe StartTrainingUiState(
                plans = samplePlans,
                selectedPlan = selectedPlan,
                selectedTraining = selectedTraining,
                selectedDate = selectedDate,
                isCreated = true
            )
        }
        trainingsRepository.observeAllTrainings().test {
            awaitItem().let { trainings ->
                trainings shouldHaveSize 1
                trainings.first().let { training ->
                    training.name shouldBe selectedTraining.name
                    training.planName shouldBe selectedPlan.name
                    training.date shouldBe selectedDate
                }
            }
        }
    }
}
