package com.maruchin.gymster.data.trainings.repository

import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.TrainingBlock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

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

    override suspend fun createTrainingBlock(plan: Plan, startDate: LocalDate): TrainingBlock {
        val newTrainingBlock = TrainingBlock.from(plan, startDate)
        collection.value += newTrainingBlock.id to newTrainingBlock
        return newTrainingBlock
    }

    override suspend fun deleteTrainingBlock(trainingBlockId: String) {
        collection.value -= trainingBlockId
    }

    override suspend fun updateProgress(
        trainingBlockId: String,
        setProgressId: String,
        newProgress: Progress
    ) {
        val trainingBlock = collection.value[trainingBlockId]!!
        val matchingTrainingWeek = trainingBlock.weeks.first { week ->
            week.trainings.any { training ->
                training.exercises.any { exercise ->
                    exercise.setProgress.any { setProgress ->
                        setProgress.id == setProgressId
                    }
                }
            }
        }
        val matchingWeekIndex = trainingBlock.weeks.indexOf(matchingTrainingWeek)
        val matchingTraining = matchingTrainingWeek.trainings.first { training ->
            training.exercises.any { exercise ->
                exercise.setProgress.any { setProgress ->
                    setProgress.id == setProgressId
                }
            }
        }
        val matchingExercise = matchingTraining.exercises.first { exercise ->
            exercise.setProgress.any { setProgress ->
                setProgress.id == setProgressId
            }
        }

        collection.value += trainingBlockId to trainingBlock.copy(
            weeks = trainingBlock.weeks.mapIndexed { index, week ->
                if (index == matchingWeekIndex) {
                    week.copy(
                        trainings = week.trainings.map { training ->
                            if (training.id == matchingTraining.id) {
                                training.copy(
                                    exercises = training.exercises.map { exercise ->
                                        if (exercise.id == matchingExercise.id) {
                                            exercise.copy(
                                                setProgress = exercise.setProgress
                                                    .map { setProgress ->
                                                        if (setProgress.id == setProgressId) {
                                                            setProgress.copy(
                                                                progress = newProgress
                                                            )
                                                        } else {
                                                            setProgress
                                                        }
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
