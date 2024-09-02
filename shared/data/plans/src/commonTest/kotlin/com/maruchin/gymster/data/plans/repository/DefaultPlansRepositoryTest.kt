package com.maruchin.gymster.data.plans.repository

import app.cash.turbine.test
import com.maruchin.gymster.core.coroutines.coreCoroutinesModule
import com.maruchin.gymster.core.database.di.coreDatabaseTestModule
import com.maruchin.gymster.data.plans.di.dataPlansModule
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.PlannedExercise
import com.maruchin.gymster.data.plans.model.PlannedTraining
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
        repository.observeAllPlans().test {
            awaitItem().shouldBeEmpty()

            val plan = repository.createPlan(name = "Push Pull")

            awaitItem() shouldContainExactly listOf(
                Plan(
                    id = plan.id,
                    name = "Push Pull",
                    trainings = emptyList()
                )
            )
        }
    }

    @Test
    fun `change plan name`() = runTest {
        val plan = repository.createPlan(name = "Push Pull")
        repository.observePlan(plan.id).test {
            awaitItem() shouldBe Plan(
                id = plan.id,
                name = "Push Pull",
                trainings = emptyList()
            )

            repository.changePlanName(planId = plan.id, newName = "Push Pull Legs")

            awaitItem() shouldBe Plan(
                id = plan.id,
                name = "Push Pull Legs",
                trainings = emptyList()
            )
        }
    }

    @Test
    fun `delete plan`() = runTest {
        val plan = repository.createPlan(name = "Push Pull")
        repository.observeAllPlans().test {
            awaitItem() shouldHaveSize 1

            repository.deletePlan(planId = plan.id)

            awaitItem().shouldBeEmpty()
        }
    }

    @Test
    fun `add week`() = runTest {
        val plan = repository.createPlan(
            name = "Push Pull"
        )
        val training = repository.addTraining(
            planId = plan.id,
            weekIndex = 0,
            name = "Push 1"
        )
        val exercise = repository.addExercise(
            planId = plan.id,
            trainingId = training.id,
            name = "Bench Press",
            sets = Sets(regular = 3, drop = 0),
            reps = Reps(10..12)
        )
        repository.observePlan(plan.id).test {
            awaitItem() shouldBe Plan(
                id = plan.id,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = training.id,
                        name = "Push 1",
                        weekIndex = 0,
                        exercises = listOf(
                            PlannedExercise(
                                id = exercise.id,
                                name = "Bench Press",
                                sets = Sets(regular = 3, drop = 0),
                                reps = Reps(10..12)
                            )
                        )
                    )
                )
            )

            val newWeek = repository.addWeek(plan.id)

            awaitItem() shouldBe Plan(
                id = plan.id,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = training.id,
                        name = "Push 1",
                        weekIndex = 0,
                        exercises = listOf(
                            PlannedExercise(
                                id = exercise.id,
                                name = "Bench Press",
                                sets = Sets(regular = 3, drop = 0),
                                reps = Reps(10..12)
                            )
                        )
                    ),
                    PlannedTraining(
                        id = newWeek[0].id,
                        name = "Push 1",
                        weekIndex = 1,
                        exercises = listOf(
                            PlannedExercise(
                                id = newWeek[0].exercises[0].id,
                                name = "Bench Press",
                                sets = Sets(regular = 3, drop = 0),
                                reps = Reps(10..12)
                            )
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `add training`() = runTest {
        val plan = repository.createPlan(name = "Push Pull")
        repository.observePlan(plan.id).test {
            awaitItem()!!.trainings.shouldBeEmpty()

            val addedTraining = repository.addTraining(
                planId = plan.id,
                weekIndex = 0,
                name = "Push 1"
            )

            awaitItem() shouldBe Plan(
                id = plan.id,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = addedTraining.id,
                        name = "Push 1",
                        weekIndex = 0,
                        exercises = emptyList()
                    )
                )
            )
        }
    }

    @Test
    fun `change training name`() = runTest {
        val plan = repository.createPlan(name = "Push Pull")
        val training = repository.addTraining(
            planId = plan.id,
            weekIndex = 0,
            name = "Push 1"
        )
        repository.observePlan(plan.id).test {
            awaitItem() shouldBe Plan(
                id = plan.id,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = training.id,
                        name = "Push 1",
                        weekIndex = 0,
                        exercises = emptyList()
                    )
                )
            )

            repository.changeTrainingName(
                planId = plan.id,
                trainingId = training.id,
                newName = "Push 2"
            )

            awaitItem() shouldBe Plan(
                id = plan.id,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = training.id,
                        name = "Push 2",
                        weekIndex = 0,
                        exercises = emptyList()
                    )
                )
            )
        }
    }

    @Test
    fun `delete training`() = runTest {
        val plan = repository.createPlan(name = "Push Pull")
        val training = repository.addTraining(
            planId = plan.id,
            weekIndex = 0,
            name = "Push 1"
        )
        repository.observePlan(plan.id).test {
            awaitItem() shouldBe Plan(
                id = plan.id,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = training.id,
                        name = "Push 1",
                        weekIndex = 0,
                        exercises = emptyList()
                    )
                )
            )

            repository.deleteTraining(planId = plan.id, trainingId = training.id)

            awaitItem() shouldBe Plan(
                id = plan.id,
                name = "Push Pull",
                trainings = emptyList()
            )
        }
    }

    @Test
    fun `add exercise`() = runTest {
        val plan = repository.createPlan(name = "Push Pull")
        val training = repository.addTraining(
            planId = plan.id,
            weekIndex = 0,
            name = "Push 1"
        )
        repository.observePlan(plan.id).test {
            awaitItem()!!.trainings.first().exercises.shouldBeEmpty()

            val exercise = repository.addExercise(
                planId = plan.id,
                trainingId = training.id,
                name = "Bench Press",
                sets = Sets(regular = 3, drop = 0),
                reps = Reps(10..12)
            )

            awaitItem() shouldBe Plan(
                id = plan.id,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = training.id,
                        name = "Push 1",
                        weekIndex = 0,
                        exercises = listOf(
                            PlannedExercise(
                                id = exercise.id,
                                name = "Bench Press",
                                sets = Sets(regular = 3, drop = 0),
                                reps = Reps(10..12)
                            )
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `update exercise`() = runTest {
        val plan = repository.createPlan(name = "Push Pull")
        val training = repository.addTraining(
            planId = plan.id,
            weekIndex = 0,
            name = "Push 1"
        )
        val exercise = repository.addExercise(
            planId = plan.id,
            trainingId = training.id,
            name = "Bench Press",
            sets = Sets(regular = 3, drop = 0),
            reps = Reps(10..12)
        )
        repository.observePlan(plan.id).test {
            awaitItem() shouldBe Plan(
                id = plan.id,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = training.id,
                        name = "Push 1",
                        weekIndex = 0,
                        exercises = listOf(
                            PlannedExercise(
                                id = exercise.id,
                                name = "Bench Press",
                                sets = Sets(regular = 3, drop = 0),
                                reps = Reps(10..12)
                            )
                        )
                    )
                )
            )

            repository.updateExercise(
                planId = plan.id,
                trainingId = training.id,
                exerciseId = exercise.id,
                newName = "Incline Bench Press",
                newSets = Sets(regular = 4, drop = 1),
                newReps = Reps(8..10)
            )

            awaitItem() shouldBe Plan(
                id = plan.id,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = training.id,
                        name = "Push 1",
                        weekIndex = 0,
                        exercises = listOf(
                            PlannedExercise(
                                id = exercise.id,
                                name = "Incline Bench Press",
                                sets = Sets(regular = 4, drop = 1),
                                reps = Reps(8..10)
                            )
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `delete exercise`() = runTest {
        val plan = repository.createPlan(name = "Push Pull")
        val training = repository.addTraining(
            planId = plan.id,
            weekIndex = 0,
            name = "Push 1"
        )
        val exercise = repository.addExercise(
            planId = plan.id,
            trainingId = training.id,
            name = "Bench Press",
            sets = Sets(regular = 3, drop = 0),
            reps = Reps(10..12)
        )
        repository.observePlan(plan.id).test {
            awaitItem() shouldBe Plan(
                id = plan.id,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = training.id,
                        name = "Push 1",
                        weekIndex = 0,
                        exercises = listOf(
                            PlannedExercise(
                                id = exercise.id,
                                name = "Bench Press",
                                sets = Sets(regular = 3, drop = 0),
                                reps = Reps(10..12)
                            )
                        )
                    )
                )
            )

            repository.deleteExercise(
                planId = plan.id,
                trainingId = training.id,
                exerciseId = exercise.id
            )

            awaitItem() shouldBe Plan(
                id = plan.id,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = training.id,
                        name = "Push 1",
                        weekIndex = 0,
                        exercises = emptyList()
                    )
                )
            )
        }
    }

    @Test
    fun `reorder exercises`() = runTest {
        val plan = repository.createPlan(name = "Push Pull")
        val training = repository.addTraining(
            planId = plan.id,
            weekIndex = 0,
            name = "Push 1"
        )
        val exercise1 = repository.addExercise(
            planId = plan.id,
            trainingId = training.id,
            name = "Bench Press",
            sets = Sets(regular = 3, drop = 0),
            reps = Reps(10..12)
        )
        val exercise2 = repository.addExercise(
            planId = plan.id,
            trainingId = training.id,
            name = "Overhead press",
            sets = Sets(regular = 3, drop = 0),
            reps = Reps(10..12)
        )
        repository.observePlan(plan.id).test {
            awaitItem() shouldBe Plan(
                id = plan.id,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = training.id,
                        name = "Push 1",
                        weekIndex = 0,
                        exercises = listOf(
                            PlannedExercise(
                                id = exercise1.id,
                                name = "Bench Press",
                                sets = Sets(regular = 3, drop = 0),
                                reps = Reps(10..12)
                            ),
                            PlannedExercise(
                                id = exercise2.id,
                                name = "Overhead press",
                                sets = Sets(regular = 3, drop = 0),
                                reps = Reps(10..12)
                            )
                        )
                    )
                )
            )

            repository.reorderExercises(
                planId = plan.id,
                trainingId = training.id,
                exercisesIds = listOf(exercise2.id, exercise1.id)
            )

            awaitItem() shouldBe Plan(
                id = plan.id,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = training.id,
                        name = "Push 1",
                        weekIndex = 0,
                        exercises = listOf(
                            PlannedExercise(
                                id = exercise2.id,
                                name = "Overhead press",
                                sets = Sets(regular = 3, drop = 0),
                                reps = Reps(10..12)
                            ),
                            PlannedExercise(
                                id = exercise1.id,
                                name = "Bench Press",
                                sets = Sets(regular = 3, drop = 0),
                                reps = Reps(10..12)
                            )
                        )
                    )
                )
            )
        }
    }
}
