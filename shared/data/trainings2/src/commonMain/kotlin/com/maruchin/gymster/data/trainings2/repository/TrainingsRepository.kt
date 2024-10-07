package com.maruchin.gymster.data.trainings2.repository

import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.PlannedTraining
import com.maruchin.gymster.data.trainings2.model.Training
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface TrainingsRepository {

    fun observeAllTrainings(): Flow<List<Training>>

    fun observeTraining(id: String): Flow<Training?>

    suspend fun addTraining(plan: Plan, plannedTraining: PlannedTraining, date: LocalDate): Training

    suspend fun updateTraining(trainingId: String, date: LocalDate)

    suspend fun deleteTraining(trainingId: String)

    suspend fun updateSetResultWeight(setResultId: String, weight: Double?)

    suspend fun updateSetResultReps(setResultId: String, reps: Int?)
}
