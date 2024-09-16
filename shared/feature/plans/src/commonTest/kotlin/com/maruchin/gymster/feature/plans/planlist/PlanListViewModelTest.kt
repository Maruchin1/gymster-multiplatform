package com.maruchin.gymster.feature.plans.planlist

import app.cash.turbine.test
import com.maruchin.gymster.data.plans.di.dataPlansTestModule
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.plans.repository.FakePlansRepository
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
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class PlanListViewModelTest : KoinTest {

    private val plansRepository: FakePlansRepository by inject()
    private val viewModel by lazy { PlanListViewModel() }

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin { modules(dataPlansTestModule) }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `emit loaded state when there is at least one plan`() = runTest {
        plansRepository.setPlans(samplePlans)

        viewModel.uiState.test {
            awaitItem() shouldBe PlanListUiState()
            awaitItem() shouldBe PlanListUiState(samplePlans)
        }
    }

    @Test
    fun `emit empty state when there is not plans`() = runTest {
        plansRepository.setPlans(emptyList())

        viewModel.uiState.test {
            awaitItem() shouldBe PlanListUiState()
        }
    }

    @Test
    fun `create plan`() = runTest {
        plansRepository.setPlans(emptyList())

        viewModel.uiState.test {
            awaitItem() shouldBe PlanListUiState()

            viewModel.createPlan(name = "Push Pull")

            awaitItem().let {
                it.plans shouldHaveSize 1
                it.plans.first().name shouldBe "Push Pull"
            }
        }
    }
}
