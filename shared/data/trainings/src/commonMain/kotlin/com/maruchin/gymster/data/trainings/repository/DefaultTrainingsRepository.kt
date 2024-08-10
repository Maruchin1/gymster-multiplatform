package com.maruchin.gymster.data.trainings.repository

import com.maruchin.gymster.core.database.schema.TrainingBlockDbModel
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.trainings.datasource.TrainingsLocalDataSource
import com.maruchin.gymster.data.trainings.mapper.toDbModel
import com.maruchin.gymster.data.trainings.mapper.toDomainModel
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.TrainingBlock
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DefaultTrainingsRepository(private val localDataSource: TrainingsLocalDataSource) :
    TrainingsRepository {

    override fun observeAllTrainingBlocks(): Flow<List<TrainingBlock>> =
        localDataSource.observeAllTrainingBlocks().map {
            it.map(TrainingBlockDbModel::toDomainModel)
        }

    override fun observeTrainingBlock(trainingBlockId: String): Flow<TrainingBlock?> =
        localDataSource.observeTrainingBlock(RealmUUID.from(trainingBlockId)).map {
            it?.toDomainModel()
        }

    override suspend fun createTrainingBlock(plan: Plan): TrainingBlock {
        val trainingBlock = TrainingBlock.from(plan)
        val trainingBlockDbModel = trainingBlock.toDbModel()
        val createdTrainingBlockDbModel = localDataSource.createTrainingBlock(trainingBlockDbModel)
        return createdTrainingBlockDbModel.toDomainModel()
    }

    override suspend fun deleteTrainingBlock(trainingBlockId: String) {
        localDataSource.deleteTrainingBlock(RealmUUID.from(trainingBlockId))
    }

    override suspend fun updateProgress(
        trainingBlockId: String,
        weekNumber: Int,
        trainingId: String,
        exerciseId: String,
        progressIndex: Int,
        newProgress: Progress
    ) {
        localDataSource.updateProgress(
            trainingBlockId = RealmUUID.from(trainingBlockId),
            weekNumber = weekNumber,
            trainingId = RealmUUID.from(trainingId),
            exerciseId = RealmUUID.from(exerciseId),
            progressIndex = progressIndex,
            newProgress = newProgress.toDbModel()
        )
    }
}
