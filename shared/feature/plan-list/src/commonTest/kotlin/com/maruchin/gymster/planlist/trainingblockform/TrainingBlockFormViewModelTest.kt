package com.maruchin.gymster.planlist.trainingblockform

import app.cash.turbine.test
import com.maruchin.gymster.data.plans.di.dataPlansTestModule
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.plans.repository.FakePlansRepository
import com.maruchin.gymster.data.trainings.di.dataTrainingsTestModule
import com.maruchin.gymster.data.trainings.repository.FakeTrainingsRepository
import com.maruchin.gymster.planlist.di.featurePlanListModule
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
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
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class TrainingBlockFormViewModelTest : KoinTest {

    private val plan = samplePlans.first()
    private val plansRepository: FakePlansRepository by inject()
    private val trainingsRepository: FakeTrainingsRepository by inject()
    private val viewModel: TrainingBlockFormViewModel by inject { parametersOf(plan.id) }

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin { modules(featurePlanListModule, dataPlansTestModule, dataTrainingsTestModule) }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `create training block`() = runTest {
        plansRepository.setPlans(samplePlans)
        val startDate = LocalDate(2024, 8, 12)

        trainingsRepository.observeAllTrainingBlocks().test {
            awaitItem().shouldBeEmpty()

            viewModel.createTrainingBlock(startDate)

            awaitItem() shouldHaveSize 1
        }
    }

    @Test
    fun `do not create training block when selected plan does not exist`() = runTest {
        plansRepository.setPlans(emptyList())
        val startDate = LocalDate(2024, 8, 12)

        trainingsRepository.observeAllTrainingBlocks().test {
            awaitItem().shouldBeEmpty()

            viewModel.createTrainingBlock(startDate).invokeOnCompletion { }

            expectNoEvents()
        }
    }
}
