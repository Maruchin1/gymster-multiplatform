package com.maruchin.multiplatform.gymster.feature.trainingplans.planeditor

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
class PlanEditorViewModelTest : KoinTest {

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
    fun `emit plan when available`() = runTest {
        trainingPlansRepository.setAvailablePlans()
        val plan = trainingPlansRepository.getPlans().random()
        val viewModel: PlanEditorViewModel = get { parametersOf(plan.id) }

        viewModel.plan.test {
            assertNull(awaitItem())

            assertEquals(plan, awaitItem())
        }
    }

    @Test
    fun `emit null when plan not available`() = runTest {
        trainingPlansRepository.setNoPlans()
        val planId = "xyz"
        val viewModel: PlanEditorViewModel = get { parametersOf(planId) }

        viewModel.plan.test {
            assertNull(awaitItem())

            expectNoEvents()
        }
    }
}
