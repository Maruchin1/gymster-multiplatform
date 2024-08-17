package com.maruchin.gymster.data.trainings.repository

import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.TrainingBlock
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface TrainingsRepository {

    fun observeAllTrainingBlocks(): Flow<List<TrainingBlock>>

    fun observeTrainingBlock(trainingBlockId: String): Flow<TrainingBlock?>

    suspend fun createTrainingBlock(plan: Plan, startDate: LocalDate): TrainingBlock

    suspend fun deleteTrainingBlock(trainingBlockId: String)

    suspend fun updateProgress(
        trainingBlockId: String,
        setProgressId: String,
        newProgress: Progress
    )
}
