package com.maruchin.gymster.feature.planeditor.trainingform

import app.cash.turbine.test
import com.maruchin.gymster.data.plans.di.dataPlansTestModule
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.plans.repository.FakePlansRepository
import com.maruchin.gymster.feature.planeditor.di.featurePlanEditorModule
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
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
import org.koin.test.get
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class DayFormViewModelTest : KoinTest {

    private val trainingPlansRepository: FakePlansRepository by inject()

    @BeforeTest
    fun setUp() {
        startKoin { modules(featurePlanEditorModule, dataPlansTestModule) }
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `emit training when selected and available`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.first()
        val training = plan.trainings.random()
        val viewModel: TrainingFormViewModel = get {
            parametersOf(plan.id, training.weekIndex, training.id)
        }

        viewModel.training.test {
            awaitItem().shouldBeNull()

            awaitItem() shouldBe training
        }
    }

    @Test
    fun `emit null when training not selected`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.first()
        val viewModel: TrainingFormViewModel = get { parametersOf(plan.id, 0, null) }

        viewModel.training.test {
            awaitItem().shouldBeNull()

            expectNoEvents()
        }
    }

    @Test
    fun `add new training on save when training not selected`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.last()
        val trainingName = "Push 1"
        val viewModel: TrainingFormViewModel = get { parametersOf(plan.id, 0, null) }

        trainingPlansRepository.observePlan(plan.id).test {
            awaitItem()!!.trainings.shouldBeEmpty()

            viewModel.saveTraining(name = trainingName)

            awaitItem()!!.let {
                it.trainings shouldHaveSize 1
                it.trainings.first().name shouldBe trainingName
            }
        }
    }

    @Test
    fun `change training name on save when training selected`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.first()
        val training = plan.trainings[2]
        val trainingNewName = "Push Hyper"
        val viewModel: TrainingFormViewModel = get {
            parametersOf(plan.id, training.weekIndex, training.id)
        }

        trainingPlansRepository.observePlan(plan.id).test {
            awaitItem()!!.trainings[2].name shouldBe training.name

            viewModel.saveTraining(name = trainingNewName)

            awaitItem()!!.trainings[2].name shouldBe trainingNewName
        }
    }
}
