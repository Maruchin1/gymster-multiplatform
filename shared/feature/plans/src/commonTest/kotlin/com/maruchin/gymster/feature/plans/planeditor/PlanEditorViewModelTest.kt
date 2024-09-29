package com.maruchin.gymster.feature.plans.planeditor

import app.cash.turbine.test
import com.maruchin.gymster.data.plans.di.dataPlansTestModule
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.plans.repository.FakePlansRepository
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldNotContain
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
import org.koin.test.KoinTest
import org.koin.test.inject

@OptIn(ExperimentalCoroutinesApi::class)
class PlanEditorViewModelTest : KoinTest {

    private val trainingPlansRepository: FakePlansRepository by inject()

    @BeforeTest
    fun setUp() {
        startKoin { modules(dataPlansTestModule) }
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `emit loaded state when plan is available`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.random()
        val viewModel = PlanEditorViewModel(plan.id)

        viewModel.uiState.test {
            awaitItem() shouldBe PlanEditorUiState()

            awaitItem() shouldBe PlanEditorUiState(plan)
        }
    }

    @Test
    fun `do not emit new state when plan is not available`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val planId = "xyz"
        val viewModel = PlanEditorViewModel(planId)

        viewModel.uiState.test {
            awaitItem() shouldBe PlanEditorUiState()

            expectNoEvents()
        }
    }

    @Test
    fun `delete plan`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.random()
        val viewModel = PlanEditorViewModel(plan.id)

        viewModel.uiState.test {
            awaitItem() shouldBe PlanEditorUiState()
            awaitItem() shouldBe PlanEditorUiState(plan)

            viewModel.deletePlan()

            awaitItem() shouldBe PlanEditorUiState(isDeleted = true)
        }
        trainingPlansRepository.observePlan(planId = plan.id).test {
            awaitItem().shouldBeNull()
        }
    }

    @Test
    fun `delete day`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.first()
        val training = plan.trainings.random()
        val trainingIndex = plan.trainings.indexOf(training)
        val viewModel = PlanEditorViewModel(plan.id)

        trainingPlansRepository.observePlan(planId = plan.id).test {
            awaitItem()!!.trainings shouldContain training

            viewModel.deleteTraining(trainingIndex = trainingIndex)

            awaitItem()!!.trainings shouldNotContain training
        }
    }

    @Test
    fun `delete exercise`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.first()
        val training = plan.trainings.first()
        val trainingIndex = plan.trainings.indexOf(training)
        val exercise = training.exercises.random()
        val exerciseIndex = training.exercises.indexOf(exercise)
        val viewModel = PlanEditorViewModel(plan.id)

        trainingPlansRepository.observePlan(planId = plan.id).test {
            awaitItem()!!.trainings.first().exercises shouldContain exercise

            viewModel.deleteExercise(trainingIndex = trainingIndex, exerciseIndex = exerciseIndex)

            awaitItem()!!.trainings.first().exercises shouldNotContain exercise
        }
    }

    @Test
    fun `reorder exercises`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.first()
        val training = plan.trainings.first()
        val trainingIndex = plan.trainings.indexOf(training)
        val originalExercises = training.exercises
        val reorderedExercises = training.exercises.shuffled()
        val viewModel = PlanEditorViewModel(plan.id)

        trainingPlansRepository.observePlan(planId = plan.id).test {
            awaitItem()!!.trainings.first().exercises shouldContainInOrder originalExercises

            viewModel.reorderExercises(
                trainingIndex = trainingIndex,
                exercisesIds = reorderedExercises.map { it.id }
            )

            awaitItem()!!.trainings.first().exercises shouldContainInOrder reorderedExercises
        }
    }
}
