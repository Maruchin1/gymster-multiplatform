package com.maruchin.gymster.data.trainings2.repository

import com.maruchin.gymster.core.database2.relation.TrainingWithExercises
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.PlannedTraining
import com.maruchin.gymster.data.trainings2.datasource.TrainingsLocalDataSource
import com.maruchin.gymster.data.trainings2.mapper.toDomainModel
import com.maruchin.gymster.data.trainings2.mapper.toEntity
import com.maruchin.gymster.data.trainings2.model.Training
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

internal class DefaultTrainingsRepository(
    private val trainingsLocalDataSource: TrainingsLocalDataSource
) : TrainingsRepository {

    override fun observeAllTrainings(): Flow<List<Training>> =
        trainingsLocalDataSource.observeAllTrainings().map {
            it.map(TrainingWithExercises::toDomainModel)
        }

    override fun observeTraining(id: String): Flow<Training?> =
        trainingsLocalDataSource.observeTraining(id).map { it?.toDomainModel() }

    override suspend fun addTraining(
        plan: Plan,
        plannedTraining: PlannedTraining,
        date: LocalDate
    ): Training {
        val training = Training.from(plan, plannedTraining, date)
        val trainingWithExercises = training.toEntity()
        trainingsLocalDataSource.addTraining(trainingWithExercises)
        return training
    }

    override suspend fun updateTraining(trainingId: String, date: LocalDate) {
        trainingsLocalDataSource.updateTraining(trainingId, date)
    }

    override suspend fun deleteTraining(trainingId: String) {
        trainingsLocalDataSource.deleteTraining(trainingId)
    }

    override suspend fun updateSetResultWeight(setResultId: String, weight: Double?) {
        trainingsLocalDataSource.updateSetResultWeight(setResultId, weight)
    }

    override suspend fun updateSetResultReps(setResultId: String, reps: Int?) {
        trainingsLocalDataSource.updateSetResultReps(setResultId, reps)
    }
}
