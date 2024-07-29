package com.maruchin.gymster.data.trainings.repository

import com.maruchin.gymster.core.database.schema.TrainingDbModel
import com.maruchin.gymster.data.plans.model.PlanDay
import com.maruchin.gymster.data.trainings.datasource.TrainingsLocalDataSource
import com.maruchin.gymster.data.trainings.mapper.toDbModel
import com.maruchin.gymster.data.trainings.mapper.toDomainModel
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.Training
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

internal class DefaultTrainingsRepository(private val localDataSource: TrainingsLocalDataSource) :
    TrainingsRepository {

    override fun observeAllTrainings(): Flow<List<Training>> =
        localDataSource.observeAllTrainings().map { it.map(TrainingDbModel::toDomainModel) }

    override fun observeTraining(trainingId: String): Flow<Training?> =
        localDataSource.observeTraining(trainingId = RealmUUID.from(trainingId)).map {
            it?.toDomainModel()
        }

    override suspend fun createTraining(date: LocalDate, planDay: PlanDay): Training {
        val newTraining = Training(date = date, planDay = planDay)
        val newTrainingDbModel = newTraining.toDbModel()
        localDataSource.createTraining(training = newTrainingDbModel)
        return newTrainingDbModel.toDomainModel()
    }

    override suspend fun updateProgress(
        trainingId: String,
        exerciseId: String,
        progressIndex: Int,
        newProgress: Progress
    ) {
        localDataSource.updateProgress(
            trainingId = RealmUUID.from(trainingId),
            exerciseId = RealmUUID.from(exerciseId),
            progressIndex = progressIndex,
            newProgress = newProgress.toDbModel()
        )
    }
}
