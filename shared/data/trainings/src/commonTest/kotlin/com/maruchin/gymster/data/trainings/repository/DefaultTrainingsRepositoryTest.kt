package com.maruchin.gymster.data.trainings.repository

import app.cash.turbine.test
import com.maruchin.gymster.core.database.di.coreDatabase2TestModule
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import com.maruchin.gymster.data.plans.model.samplePushPullLegsPlan
import com.maruchin.gymster.data.trainings.di.dataTrainings2Module
import com.maruchin.gymster.data.trainings.model.Exercise
import com.maruchin.gymster.data.trainings.model.SetResult
import com.maruchin.gymster.data.trainings.model.Training
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class DefaultTrainingsRepositoryTest : KoinTest {

    private val repository: TrainingsRepository by inject()

    @BeforeTest
    fun setup() {
        startKoin { modules(dataTrainings2Module, coreDatabase2TestModule) }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `add training`() = runTest {
        repository.observeAllTrainings().test {
            awaitItem() shouldBe emptyList()

            val training = repository.addTraining(
                plan = samplePushPullLegsPlan,
                plannedTraining = samplePushPullLegsPlan.trainings[0],
                date = LocalDate(2024, 10, 7)
            )

            // TODO find a way to insert data in one transaction
            expectMostRecentItem() shouldBe listOf(
                Training(
                    id = training.id,
                    name = "Push",
                    planName = "Push Pull Legs",
                    date = LocalDate(2024, 10, 7),
                    isComplete = false,
                    exercises = listOf(
                        Exercise(
                            id = training.exercises[0].id,
                            name = "Wyciskanie sztangi na ławce poziomej",
                            sets = Sets(regular = 3),
                            reps = Reps(4..6),
                            results = listOf(
                                SetResult(
                                    id = training.exercises[0].results[0].id,
                                    type = SetResult.Type.REGULAR,
                                    weight = null,
                                    reps = null
                                ),
                                SetResult(
                                    id = training.exercises[0].results[1].id,
                                    type = SetResult.Type.REGULAR,
                                    weight = null,
                                    reps = null
                                ),
                                SetResult(
                                    id = training.exercises[0].results[2].id,
                                    type = SetResult.Type.REGULAR,
                                    weight = null,
                                    reps = null
                                )
                            )
                        ),
                        Exercise(
                            id = training.exercises[1].id,
                            name = "Rozpiętki hantlami na ławce skos dodatni",
                            sets = Sets(regular = 2, drop = 1),
                            reps = Reps(10..12),
                            results = listOf(
                                SetResult(
                                    id = training.exercises[1].results[0].id,
                                    type = SetResult.Type.REGULAR,
                                    weight = null,
                                    reps = null
                                ),
                                SetResult(
                                    id = training.exercises[1].results[1].id,
                                    type = SetResult.Type.REGULAR,
                                    weight = null,
                                    reps = null
                                ),
                                SetResult(
                                    id = training.exercises[1].results[2].id,
                                    type = SetResult.Type.DROP,
                                    weight = null,
                                    reps = null
                                )
                            )
                        ),
                        Exercise(
                            id = training.exercises[2].id,
                            name = "Wyciskanie hantlami nad głowę siedząc",
                            sets = Sets(regular = 2, drop = 1),
                            reps = Reps(8..10),
                            results = listOf(
                                SetResult(
                                    id = training.exercises[2].results[0].id,
                                    type = SetResult.Type.REGULAR,
                                    weight = null,
                                    reps = null
                                ),
                                SetResult(
                                    id = training.exercises[2].results[1].id,
                                    type = SetResult.Type.REGULAR,
                                    weight = null,
                                    reps = null
                                ),
                                SetResult(
                                    id = training.exercises[2].results[2].id,
                                    type = SetResult.Type.DROP,
                                    weight = null,
                                    reps = null
                                )
                            )
                        ),
                        Exercise(
                            id = training.exercises[3].id,
                            name = "Wznosy hantli bokiem stojąc",
                            sets = Sets(regular = 1, drop = 3),
                            reps = Reps(10..20),
                            results = listOf(
                                SetResult(
                                    id = training.exercises[3].results[0].id,
                                    type = SetResult.Type.REGULAR,
                                    weight = null,
                                    reps = null
                                ),
                                SetResult(
                                    id = training.exercises[3].results[1].id,
                                    type = SetResult.Type.DROP,
                                    weight = null,
                                    reps = null
                                ),
                                SetResult(
                                    id = training.exercises[3].results[2].id,
                                    type = SetResult.Type.DROP,
                                    weight = null,
                                    reps = null
                                ),
                                SetResult(
                                    id = training.exercises[3].results[3].id,
                                    type = SetResult.Type.DROP,
                                    weight = null,
                                    reps = null
                                )
                            )
                        ),
                        Exercise(
                            id = training.exercises[4].id,
                            name = "Prostowanie ramion na wyciągu",
                            sets = Sets(regular = 2, drop = 1),
                            reps = Reps(10..12),
                            results = listOf(
                                SetResult(
                                    id = training.exercises[4].results[0].id,
                                    type = SetResult.Type.REGULAR,
                                    weight = null,
                                    reps = null
                                ),
                                SetResult(
                                    id = training.exercises[4].results[1].id,
                                    type = SetResult.Type.REGULAR,
                                    weight = null,
                                    reps = null
                                ),
                                SetResult(
                                    id = training.exercises[4].results[2].id,
                                    type = SetResult.Type.DROP,
                                    weight = null,
                                    reps = null
                                )
                            )
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `update training`() = runTest {
        val training = repository.addTraining(
            plan = samplePushPullLegsPlan,
            plannedTraining = samplePushPullLegsPlan.trainings[0],
            date = LocalDate(2024, 10, 7)
        )

        repository.observeTraining(training.id).test {
            awaitItem()?.date shouldBe LocalDate(2024, 10, 7)

            repository.updateTraining(
                trainingId = training.id,
                date = LocalDate(2024, 10, 14)
            )

            awaitItem()?.date shouldBe LocalDate(2024, 10, 14)
        }
    }

    @Test
    fun `delete training`() = runTest {
        val training = repository.addTraining(
            plan = samplePushPullLegsPlan,
            plannedTraining = samplePushPullLegsPlan.trainings[0],
            date = LocalDate(2024, 10, 7)
        )

        repository.observeAllTrainings().test {
            awaitItem() shouldHaveSize 1

            repository.deleteTraining(training.id)

            awaitItem() shouldHaveSize 0
        }
    }

    @Test
    fun `update set result weight`() = runTest {
        val training = repository.addTraining(
            plan = samplePushPullLegsPlan,
            plannedTraining = samplePushPullLegsPlan.trainings[0],
            date = LocalDate(2024, 10, 7)
        )
        val setResult = training.exercises.first().results.first()

        repository.observeTraining(training.id).test {
            awaitItem()?.getSetResult(setResult.id)?.weight shouldBe null

            repository.updateSetResultWeight(
                setResultId = setResult.id,
                weight = 50.0
            )

            awaitItem()?.getSetResult(setResult.id)?.weight shouldBe 50.0
        }
    }

    @Test
    fun `update set result reps`() = runTest {
        val training = repository.addTraining(
            plan = samplePushPullLegsPlan,
            plannedTraining = samplePushPullLegsPlan.trainings[0],
            date = LocalDate(2024, 10, 7)
        )
        val setResult = training.exercises.first().results.first()

        repository.observeTraining(training.id).test {
            awaitItem()?.getSetResult(setResult.id)?.reps shouldBe null

            repository.updateSetResultReps(
                setResultId = setResult.id,
                reps = 10
            )

            awaitItem()?.getSetResult(setResult.id)?.reps shouldBe 10
        }
    }
}
