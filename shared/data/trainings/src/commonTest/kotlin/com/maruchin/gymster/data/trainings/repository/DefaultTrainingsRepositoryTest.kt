package com.maruchin.gymster.data.trainings.repository

import app.cash.turbine.test
import com.maruchin.gymster.core.database.di.coreDatabaseTestModule
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.trainings.di.dataTrainingsModule
import com.maruchin.gymster.data.trainings.model.Progress
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
    fun `create training block`() = runTest {
        val plan = samplePlans.first()

        repository.observeAllTrainingBlocks().test {
            awaitItem().shouldBeEmpty()

            repository.createTrainingBlock(plan)

            awaitItem() shouldHaveSize 1
        }
    }

    @Test
    fun `delete training block`() = runTest {
        val plan = samplePlans.first()
        val trainingBlock = repository.createTrainingBlock(plan)

        repository.observeAllTrainingBlocks().test {
            awaitItem() shouldHaveSize 1

            repository.deleteTrainingBlock(trainingBlockId = trainingBlock.id)

            awaitItem().shouldBeEmpty()
        }
    }

    @Test
    fun `update progress`() = runTest {
        val plan = samplePlans.first()
        val trainingBlock = repository.createTrainingBlock(plan)
        val week = trainingBlock.weeks.first()
        val training = week.trainings.first()
        val exercise = training.exercises.first()
        val newProgress = Progress(weight = 70.0, reps = 5)

        repository.observeTrainingBlock(trainingBlock.id).test {
            awaitItem()!!
                .weeks.first()
                .trainings.first()
                .exercises.first()
                .progress.first() shouldBe Progress.empty

            repository.updateProgress(
                trainingBlockId = trainingBlock.id,
                weekNumber = week.number,
                trainingId = training.id,
                exerciseId = exercise.id,
                progressIndex = 0,
                newProgress = newProgress
            )

            awaitItem()!!
                .weeks.first()
                .trainings.first()
                .exercises.first()
                .progress.first() shouldBe newProgress
        }
    }
}
