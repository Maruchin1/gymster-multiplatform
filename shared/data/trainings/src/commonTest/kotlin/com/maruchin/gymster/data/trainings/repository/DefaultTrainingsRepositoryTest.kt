package com.maruchin.gymster.data.trainings.repository

import app.cash.turbine.test
import com.maruchin.gymster.core.database.di.coreDatabaseTestModule
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.trainings.di.dataTrainingsModule
import com.maruchin.gymster.data.trainings.model.Progress
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
        val plan = samplePlans.first()
        val planDay = plan.days.first()

        repository.observeAllTrainings().test {
            awaitItem().shouldBeEmpty()

            repository.createTraining(date = today, planName = plan.name, planDay = planDay)

            awaitItem().let { trainings ->
                trainings shouldHaveSize 1
                trainings.first().let { training ->
                    training.date shouldBe today
                    training.exercises[0].let { firstExercise ->
                        firstExercise.name shouldBe "Wyciskanie sztangi na ławce poziomej"
                        firstExercise.sets shouldBe Sets(regular = 3)
                        firstExercise.reps shouldBe Reps(4..6)
                        firstExercise.progress shouldContainExactly listOf(
                            Progress(),
                            Progress(),
                            Progress()
                        )
                    }
                    training.exercises[1].let { secondExercise ->
                        secondExercise.name shouldBe "Rozpiętki hantlami na ławce skos dodatni"
                        secondExercise.sets shouldBe Sets(regular = 2, drop = 1)
                        secondExercise.reps shouldBe Reps(10..12)
                        secondExercise.progress shouldContainExactly listOf(
                            Progress(),
                            Progress(),
                            Progress()
                        )
                    }
                    training.exercises[2].let { thirdExercise ->
                        thirdExercise.name shouldBe "Wyciskanie hantlami nad głowę siedząc"
                        thirdExercise.sets shouldBe Sets(regular = 2, drop = 1)
                        thirdExercise.reps shouldBe Reps(8..10)
                        thirdExercise.progress shouldContainExactly listOf(
                            Progress(),
                            Progress(),
                            Progress()
                        )
                    }
                    training.exercises[3].let { fourthExercise ->
                        fourthExercise.name shouldBe "Wznosy hantli bokiem stojąc"
                        fourthExercise.sets shouldBe Sets(regular = 1, drop = 3)
                        fourthExercise.reps shouldBe Reps(10..20)
                        fourthExercise.progress shouldContainExactly listOf(
                            Progress(),
                            Progress(),
                            Progress(),
                            Progress()
                        )
                    }
                    training.exercises[4].let { fifthExercise ->
                        fifthExercise.name shouldBe "Prostowanie ramion na wyciągu"
                        fifthExercise.sets shouldBe Sets(regular = 2, drop = 1)
                        fifthExercise.reps shouldBe Reps(10..12)
                        fifthExercise.progress shouldContainExactly listOf(
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
    fun `update progress`() = runTest {
        val today = LocalDate(2024, 7, 24)
        val plan = samplePlans.first()
        val planDay = plan.days.first()
        val training = repository.createTraining(
            date = today,
            planName = plan.name,
            planDay = planDay
        )
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

    @Test
    fun `delete training`() = runTest {
        val today = LocalDate(2024, 7, 24)
        val plan = samplePlans.first()
        val planDay = plan.days.first()
        val training = repository.createTraining(
            date = today,
            planName = plan.name,
            planDay = planDay
        )

        repository.observeAllTrainings().test {
            awaitItem().shouldHaveSize(1)

            repository.deleteTraining(trainingId = training.id)

            awaitItem().shouldBeEmpty()
        }
    }
}
