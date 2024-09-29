package com.maruchin.gymster.data.trainings.repository

import app.cash.turbine.test
import com.maruchin.gymster.core.coroutines.coreCoroutinesModule
import com.maruchin.gymster.core.database.di.coreDatabaseTestModule
import com.maruchin.gymster.core.datastore.di.coreSettingsTestModule
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.trainings.di.dataTrainingsModule
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldBeNull
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
        startKoin {
            modules(
                dataTrainingsModule,
                coreDatabaseTestModule,
                coreSettingsTestModule,
                coreCoroutinesModule
            )
        }
    }

    @AfterTest
    fun tearDown() {
        realm.close()
        stopKoin()
    }

    @Test
    fun `create training block`() = runTest {
        val plan = samplePlans.first()
        val startDate = LocalDate(2024, 8, 12)

        repository.observeAllTrainingBlocks().test {
            awaitItem().shouldBeEmpty()

            repository.createTrainingBlock(plan, startDate, 8)

            awaitItem() shouldHaveSize 1
        }
    }

    @Test
    fun `delete training block`() = runTest {
        val plan = samplePlans.first()
        val startDate = LocalDate(2024, 8, 12)
        val trainingBlock = repository.createTrainingBlock(plan, startDate, 8)

        repository.observeAllTrainingBlocks().test {
            awaitItem() shouldHaveSize 1

            repository.deleteTrainingBlock(trainingBlockId = trainingBlock.id)

            awaitItem().shouldBeEmpty()
        }
    }

    @Test
    fun `update set result weight`() = runTest {
        val plan = samplePlans.first()
        val startDate = LocalDate(2024, 8, 12)
        val trainingBlock = repository.createTrainingBlock(plan, startDate, 8)
        val newWeight = 70.0

        repository.observeTrainingBlock(trainingBlock.id).test {
            awaitItem()!!
                .weeks.first()
                .trainings.first()
                .exercises.first()
                .results.first()
                .weight.shouldBeNull()

            repository.updateSetResultWeight(
                trainingBlockId = trainingBlock.id,
                weekIndex = 0,
                trainingIndex = 0,
                exerciseIndex = 0,
                setIndex = 0,
                weight = newWeight
            )

            awaitItem()!!
                .weeks.first()
                .trainings.first()
                .exercises.first()
                .results.first()
                .weight shouldBe newWeight
        }
    }

    @Test
    fun `update set result reps`() = runTest {
        val plan = samplePlans.first()
        val startDate = LocalDate(2024, 8, 12)
        val trainingBlock = repository.createTrainingBlock(plan, startDate, 8)
        val newReps = 5

        repository.observeTrainingBlock(trainingBlock.id).test {
            awaitItem()!!
                .weeks.first()
                .trainings.first()
                .exercises.first()
                .results.first()
                .reps.shouldBeNull()

            repository.updateSetResultReps(
                trainingBlockId = trainingBlock.id,
                weekIndex = 0,
                trainingIndex = 0,
                exerciseIndex = 0,
                setIndex = 0,
                reps = newReps
            )

            awaitItem()!!
                .weeks.first()
                .trainings.first()
                .exercises.first()
                .results.first()
                .reps shouldBe newReps
        }
    }

    @Test
    fun `set active training block`() = runTest {
        val plan = samplePlans.first()
        val startDate = LocalDate(2024, 8, 12)
        val trainingBlock = repository.createTrainingBlock(plan, startDate, 8)

        repository.observeActiveTrainingBlock().test {
            awaitItem().shouldBeNull()

            repository.setActiveTrainingBlock(trainingBlock.id)

            awaitItem() shouldBe trainingBlock.copy(isActive = true)
        }
    }
}
