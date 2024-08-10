package com.maruchin.gymster.data.trainings.repository

import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.TrainingBlock
import kotlinx.coroutines.flow.Flow

interface TrainingsRepository {

    fun observeAllTrainingBlocks(): Flow<List<TrainingBlock>>

    fun observeTrainingBlock(trainingBlockId: String): Flow<TrainingBlock?>

    suspend fun createTrainingBlock(plan: Plan): TrainingBlock

    suspend fun deleteTrainingBlock(trainingBlockId: String)

    suspend fun updateProgress(
        trainingBlockId: String,
        weekNumber: Int,
        trainingId: String,
        exerciseId: String,
        progressIndex: Int,
        newProgress: Progress
    )
}
