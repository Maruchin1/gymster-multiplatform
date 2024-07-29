package com.maruchin.gymster.data.trainings.repository

import app.cash.turbine.test
import com.maruchin.gymster.data.trainings.di.dataTrainingsModule
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.multiplatform.gymster.shared.core.database.di.coreDatabaseTestModule
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.Reps
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.Sets
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.sampleTrainingPlans
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.realm.kotlin.Realm
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

    private val realm: Realm by inject()
    private val repository: DefaultTrainingsRepository by inject()

    @BeforeTest
    fun setUp() {
        startKoin { modules(dataTrainingsModule, coreDatabaseTestModule) }
    }

    @AfterTest
    fun tearDown() {
        realm.close()
        stopKoin()
    }

    @Test
    fun `create training`() = runTest {
        val today = LocalDate(2024, 7, 24)
        val planDay = sampleTrainingPlans.first().days.first()

        repository.observeAllTrainings().test {
            awaitItem().shouldBeEmpty()

            repository.createTraining(date = today, planDay = planDay)

            awaitItem().let { trainings ->
                trainings shouldHaveSize 1
                trainings.first().let { training ->
                    training.date shouldBe today
                    training.exercises[0].let { firstExercise ->
                        firstExercise.name shouldBe "Bench Press"
                        firstExercise.sets shouldBe Sets(regular = 3)
                        firstExercise.reps shouldBe Reps(4..6)
                        firstExercise.progress shouldContainExactly listOf(
                            Progress(),
                            Progress(),
                            Progress()
                        )
                    }
                    training.exercises[1].let { secondExercise ->
                        secondExercise.name shouldBe "Overhead Press"
                        secondExercise.sets shouldBe Sets(regular = 3)
                        secondExercise.reps shouldBe Reps(10..12)
                        secondExercise.progress shouldContainExactly listOf(
                            Progress(),
                            Progress(),
                            Progress()
                        )
                    }
                    training.exercises[2].let { thirdExercise ->
                        thirdExercise.name shouldBe "Triceps Extension"
                        thirdExercise.sets shouldBe Sets(regular = 1, drop = 2)
                        thirdExercise.reps shouldBe Reps(10..20)
                        thirdExercise.progress shouldContainExactly listOf(
                            Progress(),
                            Progress(),
                            Progress()
                        )
                    }
                }
            }
        }
    }

    @Test
    fun `should update progress`() = runTest {
        val today = LocalDate(2024, 7, 24)
        val planDay = sampleTrainingPlans.first().days.first()
        val training = repository.createTraining(date = today, planDay = planDay)
        val exercise = training.exercises.first()
        val newProgress = Progress(weight = 70.0, reps = 5)

        repository.observeTraining(trainingId = training.id).test {
            awaitItem()!!.exercises.first().progress.first() shouldBe Progress()

            repository.updateProgress(
                trainingId = training.id,
                exerciseId = exercise.id,
                progressIndex = 0,
                newProgress = newProgress
            )

            awaitItem()!!.exercises.first().progress.first() shouldBe newProgress
        }
    }
}
