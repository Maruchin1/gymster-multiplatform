package com.maruchin.multiplatform.gymster.feature.trainingplans.planform

import app.cash.turbine.test
import com.maruchin.multiplatform.gymster.data.trainingplans.di.dataTrainingPlansTestModule
import com.maruchin.multiplatform.gymster.data.trainingplans.repository.FakeTrainingPlansRepository
import com.maruchin.multiplatform.gymster.feature.trainingplans.di.featuresTrainingPlansModule
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class PlanFormViewModelTest : KoinTest {

    private val trainingPlansRepository: FakeTrainingPlansRepository by inject()

    @BeforeTest
    fun setUp() {
        startKoin { modules(featuresTrainingPlansModule, dataTrainingPlansTestModule) }
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `emit plan when selected and available`() = runTest {
        trainingPlansRepository.setAvailablePlans()
        val plan = trainingPlansRepository.getPlans().random()
        val viewModel: PlanFormViewModel = get { parametersOf(plan.id) }

        viewModel.plan.test {
            assertNull(awaitItem())

            assertEquals(plan, awaitItem())
        }
    }

    @Test
    fun `emit null when plan selected but not available`() = runTest {
        trainingPlansRepository.setNoPlans()
        val planId = "xyz"
        val viewModel: PlanFormViewModel = get { parametersOf(planId) }

        viewModel.plan.test {
            assertNull(awaitItem())
        }
    }

    @Test
    fun `emit null when plan not selected`() = runTest {
        val viewModel: PlanFormViewModel = get { parametersOf(null) }

        viewModel.plan.test {
            assertNull(awaitItem())
        }
    }

    @Test
    fun `change plan name when save and plan selected`() = runTest {
        trainingPlansRepository.setAvailablePlans()
        val plan = trainingPlansRepository.getPlans().random()
        val newName = "Eat and sleep"
        val viewModel: PlanFormViewModel = get { parametersOf(plan.id) }

        trainingPlansRepository.observePlan(plan.id).test {
            assertEquals(plan, awaitItem())

            viewModel.savePlan(newName).join()

            assertEquals(newName, awaitItem()!!.name)
        }
    }

    @Test
    fun `create new plan when save and plan not selected`() = runTest {
        val planName = "Push Pull"
        val viewModel: PlanFormViewModel = get { parametersOf(null) }

        trainingPlansRepository.observeAllPlans().test {
            assertEquals(0, awaitItem().size)

            viewModel.savePlan(planName).join()

            awaitItem().let {
                assertEquals(1, it.size)
                assertEquals(planName, it.first().name)
            }
        }
    }
}
