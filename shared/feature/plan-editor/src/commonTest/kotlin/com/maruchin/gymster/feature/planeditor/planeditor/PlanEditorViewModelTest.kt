package com.maruchin.gymster.feature.planeditor.planeditor

import app.cash.turbine.test
import com.maruchin.gymster.data.plans.di.dataPlansTestModule
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.plans.repository.FakePlansRepository
import com.maruchin.gymster.feature.planeditor.di.featurePlanEditorModule
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
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
class PlanEditorViewModelTest : KoinTest {

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
    fun `emit loaded state when plan is available`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.random()
        val viewModel: PlanEditorViewModel = get { parametersOf(plan.id) }

        viewModel.uiState.test {
            awaitItem() shouldBe PlanEditorUiState.Loading

            awaitItem() shouldBe PlanEditorUiState.Loaded(plan)
        }
    }

    @Test
    fun `do not emit new state when plan is not available`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val planId = "xyz"
        val viewModel: PlanEditorViewModel = get { parametersOf(planId) }

        viewModel.uiState.test {
            awaitItem() shouldBe PlanEditorUiState.Loading

            expectNoEvents()
        }
    }

    @Test
    fun `delete plan`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.random()
        val viewModel: PlanEditorViewModel = get { parametersOf(plan.id) }

        trainingPlansRepository.observePlan(planId = plan.id).test {
            awaitItem().shouldNotBeNull()

            viewModel.deletePlan()

            awaitItem().shouldBeNull()
        }
    }

    @Test
    fun `delete day`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.first()
        val week = plan.weeks.first()
        val training = week.trainings.random()
        val viewModel: PlanEditorViewModel = get { parametersOf(plan.id) }

        trainingPlansRepository.observePlan(planId = plan.id).test {
            awaitItem()!!.weeks.first().trainings shouldContain training

            viewModel.deleteTraining(dayId = training.id)

            awaitItem()!!.weeks.first().trainings shouldNotContain training
        }
    }

    @Test
    fun `delete exercise`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.first()
        val week = plan.weeks.first()
        val training = week.trainings.first()
        val exercise = training.exercises.random()
        val viewModel: PlanEditorViewModel = get { parametersOf(plan.id) }

        trainingPlansRepository.observePlan(planId = plan.id).test {
            awaitItem()!!.weeks.first().trainings.first().exercises shouldContain exercise

            viewModel.deleteExercise(dayId = training.id, exerciseId = exercise.id)

            awaitItem()!!.weeks.first().trainings.first().exercises shouldNotContain exercise
        }
    }

    @Test
    fun `reorder exercises`() = runTest {
        trainingPlansRepository.setPlans(samplePlans)
        val plan = samplePlans.first()
        val week = plan.weeks.first()
        val training = week.trainings.first()
        val originalExercises = training.exercises
        val reorderedExercises = training.exercises.shuffled()
        val viewModel: PlanEditorViewModel = get { parametersOf(plan.id) }

        trainingPlansRepository.observePlan(planId = plan.id).test {
            awaitItem()!!.weeks.first().trainings.first().exercises shouldContainInOrder
                originalExercises

            viewModel.reorderExercises(
                dayId = training.id,
                exercisesIds = reorderedExercises.map { it.id }
            )

            awaitItem()!!.weeks.first().trainings.first().exercises shouldContainInOrder
                reorderedExercises
        }
    }
}
