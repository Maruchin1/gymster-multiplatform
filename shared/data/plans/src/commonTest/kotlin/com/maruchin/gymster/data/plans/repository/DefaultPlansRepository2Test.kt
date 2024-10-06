package com.maruchin.gymster.data.plans.repository

import app.cash.turbine.test
import com.maruchin.gymster.core.database2.di.coreDatabase2TestModule
import com.maruchin.gymster.data.plans.di.dataPlansModule
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.PlannedExercise
import com.maruchin.gymster.data.plans.model.PlannedTraining
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class DefaultPlansRepository2Test : KoinTest {

    private val repository: PlansRepository2 by inject()

    @BeforeTest
    fun setup() {
        startKoin { modules(dataPlansModule, coreDatabase2TestModule) }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `create plan`() = runTest {
        repository.observeAllPlans().test {
            awaitItem() shouldBe emptyList()

            val planId = repository.createPlan(name = "Push Pull")

            awaitItem() shouldBe listOf(
                Plan(
                    id = planId,
                    name = "Push Pull",
                    trainings = emptyList()
                )
            )
        }
    }

    @Test
    fun `update plan`() = runTest {
        val planId = repository.createPlan(name = "Push Pull")

        repository.observePlan(planId).test {
            awaitItem() shouldBe Plan(
                id = planId,
                name = "Push Pull",
                trainings = emptyList()
            )

            repository.updatePlan(planId, "Push Pull Legs")

            awaitItem() shouldBe Plan(
                id = planId,
                name = "Push Pull Legs",
                trainings = emptyList()
            )
        }
    }

    @Test
    fun `delete plan`() = runTest {
        val planId = repository.createPlan(name = "Push Pull")

        repository.observeAllPlans().test {
            awaitItem() shouldHaveSize 1

            repository.deletePlan(planId)

            awaitItem() shouldHaveSize 0
        }
    }

    @Test
    fun `add training`() = runTest {
        val planId = repository.createPlan(name = "Push Pull")

        repository.observePlan(planId).test {
            awaitItem() shouldBe Plan(
                id = planId,
                name = "Push Pull",
                trainings = emptyList()
            )

            val trainingId = repository.addTraining(planId, "Push")

            awaitItem() shouldBe Plan(
                id = planId,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = trainingId,
                        name = "Push",
                        exercises = emptyList()
                    )
                )
            )
        }
    }

    @Test
    fun `update training`() = runTest {
        val planId = repository.createPlan(name = "Push Pull")
        val trainingId = repository.addTraining(planId, "Push")

        repository.observePlan(planId).test {
            awaitItem() shouldBe Plan(
                id = planId,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = trainingId,
                        name = "Push",
                        exercises = emptyList()
                    )
                )
            )

            repository.updateTraining(trainingId, "Pull")

            awaitItem() shouldBe Plan(
                id = planId,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = trainingId,
                        name = "Pull",
                        exercises = emptyList()
                    )
                )
            )
        }
    }

    @Test
    fun `delete training`() = runTest {
        val planId = repository.createPlan(name = "Push Pull")
        val trainingId = repository.addTraining(planId, "Push")

        repository.observePlan(planId).test {
            awaitItem() shouldBe Plan(
                id = planId,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = trainingId,
                        name = "Push",
                        exercises = emptyList()
                    )
                )
            )

            repository.deleteTraining(trainingId)

            awaitItem() shouldBe Plan(
                id = planId,
                name = "Push Pull",
                trainings = emptyList()
            )
        }
    }

    @Test
    fun `add exercise`() = runTest {
        val planId = repository.createPlan(name = "Push Pull")
        val trainingId = repository.addTraining(planId, "Push")

        repository.observePlan(planId).test {
            awaitItem() shouldBe Plan(
                id = planId,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = trainingId,
                        name = "Push",
                        exercises = emptyList()
                    )
                )
            )

            val exerciseId = repository.addExercise(
                trainingId = trainingId,
                name = "Wyciskanie sztangi na ławce poziomej",
                sets = Sets(regular = 3),
                reps = Reps(min = 4, max = 6)
            )

            awaitItem() shouldBe Plan(
                id = planId,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = trainingId,
                        name = "Push",
                        exercises = listOf(
                            PlannedExercise(
                                id = exerciseId,
                                name = "Wyciskanie sztangi na ławce poziomej",
                                sets = Sets(regular = 3),
                                reps = Reps(min = 4, max = 6)
                            )
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `update exercise`() = runTest {
        val planId = repository.createPlan(name = "Push Pull")
        val trainingId = repository.addTraining(planId, "Push")
        val exerciseId = repository.addExercise(
            trainingId = trainingId,
            name = "Wyciskanie sztangi na ławce poziomej",
            sets = Sets(regular = 3),
            reps = Reps(min = 4, max = 6)
        )

        repository.observePlan(planId).test {
            awaitItem() shouldBe Plan(
                id = planId,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = trainingId,
                        name = "Push",
                        exercises = listOf(
                            PlannedExercise(
                                id = exerciseId,
                                name = "Wyciskanie sztangi na ławce poziomej",
                                sets = Sets(regular = 3),
                                reps = Reps(min = 4, max = 6)
                            )
                        )
                    )
                )
            )

            repository.updateExercise(
                exerciseId = exerciseId,
                name = "Wyciskanie hantli nad głowę siedząc",
                sets = Sets(regular = 2, drop = 1),
                reps = Reps(min = 8, max = 10)
            )

            awaitItem() shouldBe Plan(
                id = planId,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = trainingId,
                        name = "Push",
                        exercises = listOf(
                            PlannedExercise(
                                id = exerciseId,
                                name = "Wyciskanie hantli nad głowę siedząc",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(min = 8, max = 10)
                            )
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `delete exercise`() = runTest {
        val planId = repository.createPlan(name = "Push Pull")
        val trainingId = repository.addTraining(planId, "Push")
        val exerciseId = repository.addExercise(
            trainingId = trainingId,
            name = "Wyciskanie sztangi na ławce poziomej",
            sets = Sets(regular = 3),
            reps = Reps(min = 4, max = 6)
        )

        repository.observePlan(planId).test {
            awaitItem() shouldBe Plan(
                id = planId,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = trainingId,
                        name = "Push",
                        exercises = listOf(
                            PlannedExercise(
                                id = exerciseId,
                                name = "Wyciskanie sztangi na ławce poziomej",
                                sets = Sets(regular = 3),
                                reps = Reps(min = 4, max = 6)
                            )
                        )
                    )
                )
            )

            repository.deleteExercise(exerciseId)

            awaitItem() shouldBe Plan(
                id = planId,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = trainingId,
                        name = "Push",
                        exercises = emptyList()
                    )
                )
            )
        }
    }

    @Test
    fun `reorder exercises`() = runTest {
        val planId = repository.createPlan(name = "Push Pull")
        val trainingId = repository.addTraining(planId, "Push")
        val exercise1Id = repository.addExercise(
            trainingId = trainingId,
            name = "Wyciskanie sztangi na ławce poziomej",
            sets = Sets(regular = 3),
            reps = Reps(min = 4, max = 6)
        )
        val exercise2Id = repository.addExercise(
            trainingId = trainingId,
            name = "Wyciskanie hantli nad głowę siedząc",
            sets = Sets(regular = 2, drop = 1),
            reps = Reps(min = 8, max = 10)
        )
        val exercise1 = PlannedExercise(
            id = exercise1Id,
            name = "Wyciskanie sztangi na ławce poziomej",
            sets = Sets(regular = 3),
            reps = Reps(min = 4, max = 6)
        )
        val exercise2 = PlannedExercise(
            id = exercise2Id,
            name = "Wyciskanie hantli nad głowę siedząc",
            sets = Sets(regular = 2, drop = 1),
            reps = Reps(min = 8, max = 10)
        )

        repository.observePlan(planId).test {
            awaitItem() shouldBe Plan(
                id = planId,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = trainingId,
                        name = "Push",
                        exercises = listOf(exercise1, exercise2)
                    )
                )
            )

            repository.reorderExercises(listOf(exercise2Id, exercise1Id))

            // TODO Think how to do it in one transaction. Now it requires N updates
            expectMostRecentItem() shouldBe Plan(
                id = planId,
                name = "Push Pull",
                trainings = listOf(
                    PlannedTraining(
                        id = trainingId,
                        name = "Push",
                        exercises = listOf(exercise2, exercise1)
                    )
                )
            )
        }
    }
}
