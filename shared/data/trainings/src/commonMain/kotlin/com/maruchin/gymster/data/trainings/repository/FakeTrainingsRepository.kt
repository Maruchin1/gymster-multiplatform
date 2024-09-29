package com.maruchin.gymster.data.trainings.repository

import com.maruchin.gymster.core.utils.updated
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.trainings.model.TrainingBlock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class FakeTrainingsRepository : TrainingsRepository {

    private val collection = MutableStateFlow<Map<String, TrainingBlock>>(emptyMap())
    private val activeTrainingBlockId = MutableStateFlow<String?>(null)

    fun setTrainingBlocks(trainingBlocks: List<TrainingBlock>) {
        collection.value = trainingBlocks.associateBy { it.id }
    }

    override fun observeAllTrainingBlocks(): Flow<List<TrainingBlock>> = collection.map {
        it.values.toList().map { trainingBlock ->
            trainingBlock.copy(isActive = trainingBlock.id == activeTrainingBlockId.value)
        }
    }

    override fun observeTrainingBlock(trainingBlockId: String): Flow<TrainingBlock?> =
        collection.map {
            it[trainingBlockId]?.copy(isActive = trainingBlockId == activeTrainingBlockId.value)
        }

    override fun observeActiveTrainingBlock(): Flow<TrainingBlock?> =
        activeTrainingBlockId.map { activeTrainingBlockId ->
            collection.value[activeTrainingBlockId]?.copy(isActive = true)
        }

    override suspend fun createTrainingBlock(
        plan: Plan,
        startDate: LocalDate,
        weeksDuration: Int
    ): TrainingBlock {
        val newTrainingBlock = TrainingBlock.from(plan, startDate, weeksDuration)
        collection.value += newTrainingBlock.id to newTrainingBlock
        return newTrainingBlock
    }

    override suspend fun deleteTrainingBlock(trainingBlockId: String) {
        collection.value -= trainingBlockId
    }

    override suspend fun updateSetResultWeight(
        trainingBlockId: String,
        weekIndex: Int,
        trainingIndex: Int,
        exerciseIndex: Int,
        setIndex: Int,
        weight: Double?
    ) {
        val trainingBlock = collection.value[trainingBlockId]!!
        collection.value += trainingBlockId to trainingBlock.copy(
            weeks = trainingBlock.weeks.updated(weekIndex) { week ->
                week.copy(
                    trainings = week.trainings.updated(trainingIndex) { training ->
                        training.copy(
                            exercises = training.exercises.updated(exerciseIndex) { exercise ->
                                exercise.copy(
                                    results = exercise.results.updated(setIndex) { setResult ->
                                        setResult.copy(weight = weight)
                                    }
                                )
                            }
                        )
                    }
                )
            }
        )
    }

    override suspend fun updateSetResultReps(
        trainingBlockId: String,
        weekIndex: Int,
        trainingIndex: Int,
        exerciseIndex: Int,
        setIndex: Int,
        reps: Int?
    ) {
        val trainingBlock = collection.value[trainingBlockId]!!
        collection.value += trainingBlockId to trainingBlock.copy(
            weeks = trainingBlock.weeks.updated(weekIndex) { week ->
                week.copy(
                    trainings = week.trainings.updated(trainingIndex) { training ->
                        training.copy(
                            exercises = training.exercises.updated(exerciseIndex) { exercise ->
                                exercise.copy(
                                    results = exercise.results.updated(setIndex) { setResult ->
                                        setResult.copy(reps = reps)
                                    }
                                )
                            }
                        )
                    }
                )
            }
        )
    }

    override suspend fun setActiveTrainingBlock(trainingBlockId: String) {
        activeTrainingBlockId.value = trainingBlockId
    }
}
