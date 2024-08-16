package com.maruchin.gymster.data.trainings.repository

import com.maruchin.gymster.core.database.schema.TrainingBlockDbModel
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.trainings.datasource.TrainingsLocalDataSource
import com.maruchin.gymster.data.trainings.mapper.toDbModel
import com.maruchin.gymster.data.trainings.mapper.toDomainModel
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.TrainingBlock
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

internal class DefaultTrainingsRepository(
    private val localDataSource: TrainingsLocalDataSource,
    private val scope: CoroutineScope
) : TrainingsRepository {

    override fun observeAllTrainingBlocks(): Flow<List<TrainingBlock>> =
        localDataSource.observeAllTrainingBlocks().map {
            it.map(TrainingBlockDbModel::toDomainModel)
        }

    override fun observeTrainingBlock(trainingBlockId: String): Flow<TrainingBlock?> =
        localDataSource.observeTrainingBlock(RealmUUID.from(trainingBlockId)).map {
            it?.toDomainModel()
        }

    override suspend fun createTrainingBlock(plan: Plan, startDate: LocalDate): TrainingBlock =
        scope.async {
            val trainingBlock = TrainingBlock.from(plan, startDate)
            val trainingBlockDbModel = trainingBlock.toDbModel()
            val createdTrainingBlockDbModel = localDataSource.createTrainingBlock(
                trainingBlockDbModel
            )
            createdTrainingBlockDbModel.toDomainModel()
        }.await()

    override suspend fun deleteTrainingBlock(trainingBlockId: String) = scope.async {
        localDataSource.deleteTrainingBlock(RealmUUID.from(trainingBlockId))
    }.await()

    override suspend fun updateProgress(
        trainingBlockId: String,
        weekIndex: Int,
        trainingId: String,
        exerciseId: String,
        progressIndex: Int,
        newProgress: Progress
    ) = scope.async {
        localDataSource.updateProgress(
            trainingBlockId = RealmUUID.from(trainingBlockId),
            weekIndex = weekIndex,
            trainingId = RealmUUID.from(trainingId),
            exerciseId = RealmUUID.from(exerciseId),
            progressIndex = progressIndex,
            newProgress = newProgress.toDbModel()
        )
    }.await()
}
