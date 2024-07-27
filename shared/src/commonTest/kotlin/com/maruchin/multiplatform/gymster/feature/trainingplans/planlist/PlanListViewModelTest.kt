package com.maruchin.multiplatform.gymster.feature.trainingplans.planlist

import app.cash.turbine.test
import com.maruchin.multiplatform.gymster.data.trainingplans.di.dataTrainingPlansTestModule
import com.maruchin.multiplatform.gymster.data.trainingplans.repository.FakeTrainingPlansRepository
import com.maruchin.multiplatform.gymster.feature.trainingplans.di.featuresTrainingPlansModule
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
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
class PlanListViewModelTest : KoinTest {

    private val trainingPlansRepository: FakeTrainingPlansRepository by inject()
    private val viewModel: PlanListViewModel by inject()

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
    fun `emit loaded state when training plans available`() = runTest {
        trainingPlansRepository.setAvailablePlans()
        val allPlans = trainingPlansRepository.getPlans()

        viewModel.uiState.test {
            assertEquals(PlanListUiState.Loading, awaitItem())

            assertEquals(PlanListUiState.Loaded(allPlans), awaitItem())
        }
    }

    @Test
    fun `emit empty state when no training plans`() = runTest {
        trainingPlansRepository.setNoPlans()

        viewModel.uiState.test {
            assertEquals(PlanListUiState.Loading, awaitItem())

            assertEquals(PlanListUiState.Empty, awaitItem())
        }
    }
}
