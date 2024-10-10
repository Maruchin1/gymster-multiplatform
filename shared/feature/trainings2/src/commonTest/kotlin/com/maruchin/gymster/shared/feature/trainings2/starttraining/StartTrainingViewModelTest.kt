package com.maruchin.gymster.shared.feature.trainings2.starttraining

import app.cash.turbine.test
import com.maruchin.gymster.data.plans.di.dataPlansTestModule
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.plans.repository.FakePlansRepository
import com.maruchin.gymster.data.trainings2.di.dataTrainings2TestModule
import com.maruchin.gymster.data.trainings2.repository.FakeTrainingsRepository
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
    private val viewModel by lazy { StartTrainingViewModel() }

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin { modules(dataPlansTestModule, dataTrainings2TestModule) }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `emit all plans`() = runTest {
        plansRepository.setPlans(samplePlans)

        viewModel.uiState.test {
            awaitItem() shouldBe StartTrainingUiState()
            awaitItem() shouldBe StartTrainingUiState(samplePlans)
        }
    }

    @Test
    fun `select plan`() = runTest {
        plansRepository.setPlans(samplePlans)
        val selectedPlan = samplePlans.first()

        viewModel.uiState.test {
            awaitItem() shouldBe StartTrainingUiState()
            awaitItem() shouldBe StartTrainingUiState(samplePlans)

            viewModel.selectPlan(selectedPlan)
            awaitItem() shouldBe StartTrainingUiState(samplePlans, selectedPlan)
        }
    }

    @Test
    fun `reset plan`() = runTest {
        plansRepository.setPlans(samplePlans)
        val selectedPlan = samplePlans.first()

        viewModel.uiState.test {
            awaitItem() shouldBe StartTrainingUiState()
            awaitItem() shouldBe StartTrainingUiState(samplePlans)

            viewModel.selectPlan(selectedPlan)
            awaitItem() shouldBe StartTrainingUiState(samplePlans, selectedPlan)

            viewModel.resetPlan()
            awaitItem() shouldBe StartTrainingUiState(samplePlans)
        }
    }

    @Test
    fun `select training`() = runTest {
        plansRepository.setPlans(samplePlans)
        val selectedPlan = samplePlans.first()
        val selectedTraining = selectedPlan.trainings.first()

        viewModel.uiState.test {
            awaitItem() shouldBe StartTrainingUiState()
            awaitItem() shouldBe StartTrainingUiState(samplePlans)

            viewModel.selectPlan(selectedPlan)
            awaitItem() shouldBe StartTrainingUiState(samplePlans, selectedPlan)

            viewModel.selectTraining(selectedTraining)
            awaitItem() shouldBe StartTrainingUiState(samplePlans, selectedPlan, selectedTraining)
        }
    }

    @Test
    fun `reset training`() = runTest {
        plansRepository.setPlans(samplePlans)
        val selectedPlan = samplePlans.first()
        val selectedTraining = selectedPlan.trainings.first()

        viewModel.uiState.test {
            awaitItem() shouldBe StartTrainingUiState()
            awaitItem() shouldBe StartTrainingUiState(samplePlans)

            viewModel.selectPlan(selectedPlan)
            awaitItem() shouldBe StartTrainingUiState(samplePlans, selectedPlan)

            viewModel.selectTraining(selectedTraining)
            awaitItem() shouldBe StartTrainingUiState(samplePlans, selectedPlan, selectedTraining)

            viewModel.resetTraining()
            awaitItem() shouldBe StartTrainingUiState(samplePlans, selectedPlan)
        }
    }

    @Test
    fun `select date`() = runTest {
        plansRepository.setPlans(samplePlans)
        val selectedPlan = samplePlans.first()
        val selectedTraining = selectedPlan.trainings.first()
        val selectedDate = LocalDate(2024, 10, 9)

        viewModel.uiState.test {
            awaitItem() shouldBe StartTrainingUiState()
            awaitItem() shouldBe StartTrainingUiState(samplePlans)

            viewModel.selectPlan(selectedPlan)
            awaitItem() shouldBe StartTrainingUiState(samplePlans, selectedPlan)

            viewModel.selectTraining(selectedTraining)
            awaitItem() shouldBe StartTrainingUiState(samplePlans, selectedPlan, selectedTraining)

            viewModel.selectDate(selectedDate)
            awaitItem() shouldBe StartTrainingUiState(
                samplePlans,
                selectedPlan,
                selectedTraining,
                selectedDate
            )
        }
    }

    @Test
    fun `reset date`() = runTest {
        plansRepository.setPlans(samplePlans)
        val selectedPlan = samplePlans.first()
        val selectedTraining = selectedPlan.trainings.first()
        val selectedDate = LocalDate(2024, 10, 9)

        viewModel.uiState.test {
            awaitItem() shouldBe StartTrainingUiState()
            awaitItem() shouldBe StartTrainingUiState(samplePlans)

            viewModel.selectPlan(selectedPlan)
            awaitItem() shouldBe StartTrainingUiState(samplePlans, selectedPlan)

            viewModel.selectTraining(selectedTraining)
            awaitItem() shouldBe StartTrainingUiState(samplePlans, selectedPlan, selectedTraining)

            viewModel.selectDate(selectedDate)
            awaitItem() shouldBe StartTrainingUiState(
                samplePlans,
                selectedPlan,
                selectedTraining,
                selectedDate
            )

            viewModel.resetDate()
            awaitItem() shouldBe StartTrainingUiState(
                samplePlans,
                selectedPlan,
                selectedTraining
            )
        }
    }

    @Test
    fun `start training`() = runTest {
        plansRepository.setPlans(samplePlans)
        val selectedPlan = samplePlans.first()
        val selectedTraining = selectedPlan.trainings.first()
        val selectedDate = LocalDate(2024, 10, 9)

        viewModel.uiState.test {
            awaitItem() shouldBe StartTrainingUiState()
            awaitItem() shouldBe StartTrainingUiState(samplePlans)

            viewModel.selectPlan(selectedPlan)
            awaitItem() shouldBe StartTrainingUiState(samplePlans, selectedPlan)

            viewModel.selectTraining(selectedTraining)
            awaitItem() shouldBe StartTrainingUiState(samplePlans, selectedPlan, selectedTraining)

            viewModel.selectDate(selectedDate)
            awaitItem() shouldBe StartTrainingUiState(
                samplePlans,
                selectedPlan,
                selectedTraining,
                selectedDate
            )

            viewModel.startTraining()
            awaitItem() shouldBe StartTrainingUiState(
                samplePlans,
                selectedPlan,
                selectedTraining,
                selectedDate,
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
