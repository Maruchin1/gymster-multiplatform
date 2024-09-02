package com.maruchin.gymster.feature.planeditor.durationform

import app.cash.turbine.test
import com.maruchin.gymster.data.plans.di.dataPlansTestModule
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.plans.repository.FakePlansRepository
import com.maruchin.gymster.feature.planeditor.di.featurePlanEditorModule
import io.kotest.matchers.nulls.shouldBeNull
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
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class DurationFormViewModelTest : KoinTest {

    private val plan = samplePlans.first()
    private val plansRepository: FakePlansRepository by inject()
    private val viewModel: DurationFormViewModel by inject { parametersOf(plan.id) }

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        startKoin { modules(featurePlanEditorModule, dataPlansTestModule) }
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun `emit plan when available`() = runTest {
        plansRepository.setPlans(samplePlans)

        viewModel.plan.test {
            awaitItem().shouldBeNull()

            awaitItem() shouldBe plan
        }
    }

    @Test
    fun `don't emit plan when not available`() = runTest {
        viewModel.plan.test {
            awaitItem().shouldBeNull()

            expectNoEvents()
        }
    }
}
