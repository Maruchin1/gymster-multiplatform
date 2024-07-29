package com.maruchin.gymster.data.trainings.repository

import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.Training
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.TrainingPlanDay
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface TrainingsRepository {

    fun observeAllTrainings(): Flow<List<Training>>

    fun observeTraining(trainingId: String): Flow<Training?>

    suspend fun createTraining(date: LocalDate, planDay: TrainingPlanDay): Training

    suspend fun updateProgress(
        trainingId: String,
        exerciseId: String,
        progressIndex: Int,
        newProgress: Progress
    )
}
