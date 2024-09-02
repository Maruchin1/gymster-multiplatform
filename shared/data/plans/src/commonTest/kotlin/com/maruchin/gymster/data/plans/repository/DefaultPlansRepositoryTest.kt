package com.maruchin.gymster.data.plans.repository

import app.cash.turbine.test
import com.maruchin.gymster.core.coroutines.coreCoroutinesModule
import com.maruchin.gymster.core.database.di.coreDatabaseTestModule
import com.maruchin.gymster.data.plans.di.dataPlansModule
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.realm.kotlin.Realm
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class DefaultPlansRepositoryTest : KoinTest {
    private val realm: Realm by inject()
    private val repository: PlansRepository by inject()

    @BeforeTest
    fun setup() {
        startKoin { modules(dataPlansModule, coreDatabaseTestModule, coreCoroutinesModule) }
    }

    @AfterTest
    fun tearDown() {
        realm.close()
        stopKoin()
    }

    @Test
    fun `create plan`() = runTest {
        val name = "Push Pull"
        repository.observeAllPlans().test {
            awaitItem().shouldBeEmpty()

            repository.createPlan(name = name)

            awaitItem().let {
                it shouldHaveSize 1
                it.first().name shouldBe name
            }
        }
    }

    @Test
    fun `change plan name`() = runTest {
        val originalName = "Push Pull"
        val newName = "Push Pull Legs"
        val plan = repository.createPlan(name = originalName)
        repository.observePlan(plan.id).test {
            awaitItem()!!.name shouldBe originalName

            repository.changePlanName(planId = plan.id, newName = newName)

            awaitItem()!!.name shouldBe newName
        }
    }

    @Test
    fun `change plan duration`() = runTest {
        val planName = "Push Pull"
        val plan = repository.createPlan(name = planName)
        val newDuration = 8
        repository.observePlan(plan.id).test {
            awaitItem()!!.weeksDuration shouldBe Plan.DEFAULT_WEEKS_DURATION

            repository.changePlanDuration(planId = plan.id, newDuration = newDuration)

            awaitItem()!!.weeksDuration shouldBe newDuration
        }
    }

    @Test
    fun `delete plan`() = runTest {
        val name = "Push Pull"
        val plan = repository.createPlan(name = name)
        repository.observeAllPlans().test {
            awaitItem() shouldHaveSize 1

            repository.deletePlan(planId = plan.id)

            awaitItem().shouldBeEmpty()
        }
    }

    @Test
    fun `add day`() = runTest {
        val planName = "Push Pull"
        val plan = repository.createPlan(planName)
        val weekIndex = 0
        val trainingName = "Push 1"
        repository.observePlan(plan.id).test {
            awaitItem()!!.trainings.shouldBeEmpty()

            repository.addTraining(planId = plan.id, weekIndex = weekIndex, name = trainingName)

            awaitItem()!!.trainings.let {
                it shouldHaveSize 1
                it.first().name shouldBe trainingName
            }
        }
    }

    @Test
    fun `change day name`() = runTest {
        val planName = "Push Pull"
        val plan = repository.createPlan(name = planName)
        val weekIndex = 0
        val trainingName = "Push 1"
        val training = repository.addTraining(
            planId = plan.id,
            weekIndex = weekIndex,
            name = trainingName
        )
        val updatedTrainingName = "Push 2"
        repository.observePlan(plan.id).test {
            awaitItem()!!.trainings.first().name shouldBe trainingName

            repository.changeTrainingName(
                planId = plan.id,
                trainingId = training.id,
                newName = updatedTrainingName
            )

            awaitItem()!!.trainings.first().name shouldBe updatedTrainingName
        }
    }

    @Test
    fun `delete day`() = runTest {
        val planName = "Push Pull"
        val plan = repository.createPlan(name = planName)
        val weekIndex = 0
        val trainingName = "Push 1"
        val training = repository.addTraining(
            planId = plan.id,
            weekIndex = weekIndex,
            name = trainingName
        )
        repository.observePlan(plan.id).test {
            awaitItem()!!.trainings shouldHaveSize 1

            repository.deleteTraining(planId = plan.id, trainingId = training.id)

            awaitItem()!!.trainings.shouldBeEmpty()
        }
    }

    @Test
    fun `add exercise`() = runTest {
        val planName = "Push Pull"
        val plan = repository.createPlan(name = planName)
        val weekIndex = 0
        val trainingName = "Push 1"
        val training = repository.addTraining(
            planId = plan.id,
            weekIndex = weekIndex,
            name = trainingName
        )
        val exerciseName = "Bench Press"
        val exerciseSets = Sets(regular = 3, drop = 0)
        val exerciseReps = Reps(10..12)
        repository.observePlan(plan.id).test {
            awaitItem()!!.trainings.first().exercises.shouldBeEmpty()

            repository.addExercise(
                planId = plan.id,
                trainingId = training.id,
                name = exerciseName,
                sets = exerciseSets,
                reps = exerciseReps
            )

            awaitItem()!!.let { plan ->
                plan.trainings.first().exercises.let { exercises ->
                    exercises shouldHaveSize 1
                    exercises.first().let { exercise ->
                        exercise.name shouldBe exerciseName
                        exercise.sets shouldBe exerciseSets
                        exercise.reps shouldBe exerciseReps
                    }
                }
            }
        }
    }

    @Test
    fun `update exercise`() = runTest {
        val planName = "Push Pull"
        val plan = repository.createPlan(name = planName)
        val weekIndex = 0
        val trainingName = "Push 1"
        val training = repository.addTraining(
            planId = plan.id,
            weekIndex = weekIndex,
            name = trainingName
        )
        val exerciseName = "Bench Press"
        val exerciseSets = Sets(regular = 3, drop = 0)
        val exerciseReps = Reps(10..12)
        val exercise = repository.addExercise(
            planId = plan.id,
            trainingId = training.id,
            name = exerciseName,
            sets = exerciseSets,
            reps = exerciseReps
        )
        val updatedExerciseName = "Incline Bench Press"
        val updatedSets = Sets(regular = 4, drop = 1)
        val updatedReps = Reps(8..10)
        repository.observePlan(plan.id).test {
            awaitItem()!!.trainings.first().exercises.first().name shouldBe exerciseName

            repository.updateExercise(
                planId = plan.id,
                trainingId = training.id,
                exerciseId = exercise.id,
                newName = updatedExerciseName,
                newSets = updatedSets,
                newReps = updatedReps
            )

            awaitItem()!!.trainings.first().exercises.first().name shouldBe updatedExerciseName
        }
    }

    @Test
    fun `delete exercise`() = runTest {
        val planName = "Push Pull"
        val plan = repository.createPlan(name = planName)
        val weekIndex = 0
        val trainingName = "Push 1"
        val training = repository.addTraining(
            planId = plan.id,
            weekIndex = weekIndex,
            name = trainingName
        )
        val exerciseName = "Bench Press"
        val exerciseSets = Sets(regular = 3, drop = 0)
        val exerciseReps = Reps(10..12)
        val exercise = repository.addExercise(
            planId = plan.id,
            trainingId = training.id,
            name = exerciseName,
            sets = exerciseSets,
            reps = exerciseReps
        )
        repository.observePlan(plan.id).test {
            awaitItem()!!.trainings.first().exercises shouldHaveSize 1

            repository.deleteExercise(
                planId = plan.id,
                trainingId = training.id,
                exerciseId = exercise.id
            )

            awaitItem()!!.trainings.first().exercises.shouldBeEmpty()
        }
    }

    @Test
    fun `reorder exercises`() = runTest {
        val planName = "Push Pull"
        val plan = repository.createPlan(name = planName)
        val weekIndex = 0
        val trainingName = "Push"
        val training = repository.addTraining(
            planId = plan.id,
            weekIndex = weekIndex,
            name = trainingName
        )
        val firstExerciseName = "Bench press"
        val fistExerciseSets = Sets(regular = 3, drop = 0)
        val firstExerciseReps = Reps(10..12)
        val exercise1 = repository.addExercise(
            planId = plan.id,
            trainingId = training.id,
            name = firstExerciseName,
            sets = fistExerciseSets,
            reps = firstExerciseReps
        )
        val secondExerciseName = "Overhead press"
        val secondExerciseSets = Sets(regular = 3, drop = 0)
        val secondExerciseReps = Reps(10..12)
        val exercise2 = repository.addExercise(
            planId = plan.id,
            trainingId = training.id,
            name = secondExerciseName,
            sets = secondExerciseSets,
            reps = secondExerciseReps
        )
        repository.observeAllPlans().test {
            awaitItem().first().trainings.first().exercises shouldContainExactly listOf(
                exercise1,
                exercise2
            )

            repository.reorderExercises(
                planId = plan.id,
                trainingId = training.id,
                exercisesIds = listOf(exercise2.id, exercise1.id)
            )

            awaitItem().first().trainings.first().exercises shouldContainExactly listOf(
                exercise2,
                exercise1
            )
        }
    }
}
