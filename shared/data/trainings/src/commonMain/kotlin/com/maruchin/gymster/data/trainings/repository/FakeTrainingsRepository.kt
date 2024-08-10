package com.maruchin.gymster.data.trainings.repository

import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.TrainingBlock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeTrainingsRepository : TrainingsRepository {

    private val collection = MutableStateFlow<Map<String, TrainingBlock>>(emptyMap())

    fun setTrainingBlocks(trainingBlocks: List<TrainingBlock>) {
        collection.value = trainingBlocks.associateBy { it.id }
    }

    override fun observeAllTrainingBlocks(): Flow<List<TrainingBlock>> = collection.map {
        it.values.toList()
    }

    override fun observeTrainingBlock(trainingBlockId: String): Flow<TrainingBlock?> =
        collection.map { it[trainingBlockId] }

    override suspend fun createTrainingBlock(plan: Plan): TrainingBlock {
        val newTrainingBlock = TrainingBlock.from(plan)
        collection.value += newTrainingBlock.id to newTrainingBlock
        return newTrainingBlock
    }

    override suspend fun deleteTrainingBlock(trainingBlockId: String) {
        collection.value -= trainingBlockId
    }

    override suspend fun updateProgress(
        trainingBlockId: String,
        weekNumber: Int,
        trainingId: String,
        exerciseId: String,
        progressIndex: Int,
        newProgress: Progress
    ) {
        collection.value +=
            trainingBlockId to collection.value[trainingBlockId]!!.let { trainingBlock ->
                trainingBlock.copy(
                    weeks = trainingBlock.weeks.map { week ->
                        if (week.number == weekNumber) {
                            week.copy(
                                trainings = week.trainings.map { training ->
                                    if (training.id == trainingId) {
                                        training.copy(
                                            exercises = training.exercises.map { exercise ->
                                                if (exercise.id == exerciseId) {
                                                    exercise.copy(
                                                        progress = exercise.progress.toMutableList()
                                                            .apply {
                                                                set(progressIndex, newProgress)
                                                            }
                                                    )
                                                } else {
                                                    exercise
                                                }
                                            }
                                        )
                                    } else {
                                        training
                                    }
                                }
                            )
                        } else {
                            week
                        }
                    }
                )
            }
    }
}
