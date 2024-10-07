package com.maruchin.gymster.data.trainings2.repository

import com.maruchin.gymster.core.utils.updated
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.PlannedTraining
import com.maruchin.gymster.data.trainings2.model.Training
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate

class FakeTrainingsRepository : TrainingsRepository {

    private val state = MutableStateFlow(emptyList<Training>())

    override fun observeAllTrainings(): Flow<List<Training>> = state

    override fun observeTraining(id: String): Flow<Training?> = state.map { trainings ->
        trainings.find { it.id == id }
    }

    override suspend fun addTraining(
        plan: Plan,
        plannedTraining: PlannedTraining,
        date: LocalDate
    ): Training {
        val training = Training.from(plan, plannedTraining, date)
        state.value += training
        return training
    }

    override suspend fun updateTraining(trainingId: String, date: LocalDate) {
        state.update { trainings ->
            trainings.updated({ it.id == trainingId }) { training ->
                training.copy(date = date)
            }
        }
    }

    override suspend fun deleteTraining(trainingId: String) {
        state.update { trainings ->
            trainings.filter { it.id != trainingId }
        }
    }

    override suspend fun updateSetResultWeight(setResultId: String, weight: Double?) {
        state.update { trainings ->
            trainings.updated({ it.hasSetResult(setResultId) }) { training ->
                training.copy(
                    exercises = training.exercises.updated(
                        { it.hasSetResult(setResultId) }
                    ) { exercise ->
                        exercise.copy(
                            results = exercise.results.updated(
                                { it.id == setResultId }
                            ) { setResult ->
                                setResult.copy(weight = weight)
                            }
                        )
                    }
                )
            }
        }
    }

    override suspend fun updateSetResultReps(setResultId: String, reps: Int?) {
        state.update { trainings ->
            trainings.updated({ it.hasSetResult(setResultId) }) { training ->
                training.copy(
                    exercises = training.exercises.updated(
                        { it.hasSetResult(setResultId) }
                    ) { exercise ->
                        exercise.copy(
                            results = exercise.results.updated(
                                { it.id == setResultId }
                            ) { setResult ->
                                setResult.copy(reps = reps)
                            }
                        )
                    }
                )
            }
        }
    }
}
