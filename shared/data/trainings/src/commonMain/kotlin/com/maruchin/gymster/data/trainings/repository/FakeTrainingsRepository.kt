package com.maruchin.gymster.data.trainings.repository

import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.Training
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.TrainingPlanDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class FakeTrainingsRepository : TrainingsRepository {

    private val collection = MutableStateFlow<Map<String, Training>>(emptyMap())

    fun setTrainings(trainings: List<Training>) {
        collection.value = trainings.associateBy { it.id }
    }

    override fun observeAllTrainings(): Flow<List<Training>> = collection.map { it.values.toList() }

    override fun observeTraining(trainingId: String): Flow<Training?> = collection.map {
        it[trainingId]
    }

    override suspend fun createTraining(date: LocalDate, planDay: TrainingPlanDay): Training {
        val trainings = collection.value
        val id = (trainings.size + 1).toString()
        val newTraining = Training(date = date, planDay = planDay).copy(id = id)
        collection.value += id to newTraining
        return newTraining
    }

    override suspend fun updateProgress(
        trainingId: String,
        exerciseId: String,
        progressIndex: Int,
        newProgress: Progress
    ) {
        collection.value[trainingId]?.let { training ->
            val updatedExercises = training.exercises.map { exercise ->
                if (exercise.id == exerciseId) {
                    val updatedProgress = exercise.progress.toMutableList()
                    updatedProgress[progressIndex] = newProgress
                    exercise.copy(progress = updatedProgress)
                } else {
                    exercise
                }
            }
            collection.value += trainingId to training.copy(exercises = updatedExercises)
        }
    }
}
