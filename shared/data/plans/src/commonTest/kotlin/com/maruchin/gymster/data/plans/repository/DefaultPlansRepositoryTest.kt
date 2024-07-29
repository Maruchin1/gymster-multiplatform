package com.maruchin.gymster.data.plans.repository

import app.cash.turbine.test
import com.maruchin.gymster.core.database.di.coreDatabaseTestModule
import com.maruchin.gymster.data.plans.di.dataPlansModule
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import io.kotest.matchers.collections.shouldBeEmpty
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
        startKoin { modules(dataPlansModule, coreDatabaseTestModule) }
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
        val planId = repository.createPlan(name = originalName)
        repository.observePlan(planId).test {
            awaitItem()!!.name shouldBe originalName

            repository.changePlanName(planId = planId, newName = newName)

            awaitItem()!!.name shouldBe newName
        }
    }

    @Test
    fun `delete plan`() = runTest {
        val name = "Push Pull"
        val planId = repository.createPlan(name = name)
        repository.observeAllPlans().test {
            awaitItem() shouldHaveSize 1

            repository.deletePlan(planId = planId)

            awaitItem().shouldBeEmpty()
        }
    }

    @Test
    fun `add day`() = runTest {
        val planName = "Push Pull"
        val planId = repository.createPlan(planName)
        val dayName = "Push 1"
        repository.observePlan(planId).test {
            awaitItem()!!.days.shouldBeEmpty()

            repository.addDay(planId = planId, name = dayName)

            awaitItem()!!.days.let {
                it shouldHaveSize 1
                it.first().name shouldBe dayName
            }
        }
    }

    @Test
    fun `change day name`() = runTest {
        val planName = "Push Pull"
        val planId = repository.createPlan(name = planName)
        val dayName = "Push 1"
        val dayId = repository.addDay(planId = planId, name = dayName)
        val updatedDayName = "Push 2"
        repository.observePlan(planId).test {
            awaitItem()!!.days.first().name shouldBe dayName

            repository.changeDayName(
                planId = planId,
                dayId = dayId,
                newName = updatedDayName
            )

            awaitItem()!!.days.first().name shouldBe updatedDayName
        }
    }

    @Test
    fun `delete day`() = runTest {
        val planName = "Push Pull"
        val planId = repository.createPlan(name = planName)
        val dayName = "Push 1"
        val dayId = repository.addDay(planId = planId, name = dayName)
        repository.observePlan(planId).test {
            awaitItem()!!.days shouldHaveSize 1

            repository.deleteDay(planId = planId, dayId = dayId)

            awaitItem()!!.days.shouldBeEmpty()
        }
    }

    @Test
    fun `add exercise`() = runTest {
        val planName = "Push Pull"
        val planId = repository.createPlan(name = planName)
        val dayName = "Push 1"
        val dayId = repository.addDay(planId = planId, name = dayName)
        val exerciseName = "Bench Press"
        val exerciseSets = Sets(regular = 3, drop = 0)
        val exerciseReps = Reps(10..12)
        repository.observePlan(planId).test {
            awaitItem()!!.days.first().exercises.shouldBeEmpty()

            repository.addExercise(
                planId = planId,
                dayId = dayId,
                name = exerciseName,
                sets = exerciseSets,
                reps = exerciseReps
            )

            awaitItem()!!.let { plan ->
                plan.days.first().exercises.let { exercises ->
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
        val planId = repository.createPlan(name = planName)
        val dayName = "Push 1"
        val dayId = repository.addDay(planId = planId, name = dayName)
        val exerciseName = "Bench Press"
        val exerciseSets = Sets(regular = 3, drop = 0)
        val exerciseReps = Reps(10..12)
        val exerciseId = repository.addExercise(
            planId = planId,
            dayId = dayId,
            name = exerciseName,
            sets = exerciseSets,
            reps = exerciseReps
        )
        val updatedExerciseName = "Incline Bench Press"
        val updatedSets = Sets(regular = 4, drop = 1)
        val updatedReps = Reps(8..10)
        repository.observePlan(planId).test {
            awaitItem()!!.days.first().exercises.first().name shouldBe exerciseName

            repository.updateExercise(
                planId = planId,
                dayId = dayId,
                exerciseId = exerciseId,
                newName = updatedExerciseName,
                newSets = updatedSets,
                newReps = updatedReps
            )

            awaitItem()!!.days.first().exercises.first().name shouldBe updatedExerciseName
        }
    }

    @Test
    fun `delete exercise`() = runTest {
        val planName = "Push Pull"
        val planId = repository.createPlan(name = planName)
        val dayName = "Push 1"
        val dayId = repository.addDay(planId = planId, name = dayName)
        val exerciseName = "Bench Press"
        val exerciseSets = Sets(regular = 3, drop = 0)
        val exerciseReps = Reps(10..12)
        val exerciseId = repository.addExercise(
            planId = planId,
            dayId = dayId,
            name = exerciseName,
            sets = exerciseSets,
            reps = exerciseReps
        )
        repository.observePlan(planId).test {
            awaitItem()!!.days.first().exercises shouldHaveSize 1

            repository.deleteExercise(
                planId = planId,
                dayId = dayId,
                exerciseId = exerciseId
            )

            awaitItem()!!.days.first().exercises.shouldBeEmpty()
        }
    }

    @Test
    fun `reorder exercises`() = runTest {
        val planName = "Push Pull"
        val planId = repository.createPlan(name = planName)
        val dayName = "Push"
        val dayId = repository.addDay(planId = planId, name = dayName)
        val firstExerciseName = "Bench press"
        val fistExerciseSets = Sets(regular = 3, drop = 0)
        val firstExerciseReps = Reps(10..12)
        val exerciseId1 = repository.addExercise(
            planId = planId,
            dayId = dayId,
            name = firstExerciseName,
            sets = fistExerciseSets,
            reps = firstExerciseReps
        )
        val secondExerciseName = "Overhead press"
        val secondExerciseSets = Sets(regular = 3, drop = 0)
        val secondExerciseReps = Reps(10..12)
        val exerciseId2 = repository.addExercise(
            planId = planId,
            dayId = dayId,
            name = secondExerciseName,
            sets = secondExerciseSets,
            reps = secondExerciseReps
        )
        repository.observeAllPlans().test {
            awaitItem().first().days.first().exercises.let {
                it[0].id shouldBe exerciseId1
                it[1].id shouldBe exerciseId2
            }

            repository.reorderExercises(
                planId = planId,
                dayId = dayId,
                exercisesIds = listOf(exerciseId2, exerciseId1)
            )

            awaitItem().first().days.first().exercises.let {
                it[0].id shouldBe exerciseId2
                it[1].id shouldBe exerciseId1
            }
        }
    }
}
