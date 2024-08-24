package com.maruchin.gymster.data.trainings.repository

import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.trainings.datasource.ActiveTrainingLocalDataSource
import com.maruchin.gymster.data.trainings.datasource.TrainingsLocalDataSource
import com.maruchin.gymster.data.trainings.mapper.toDbModel
import com.maruchin.gymster.data.trainings.mapper.toDomainModel
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.TrainingBlock
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.LocalDate

internal class DefaultTrainingsRepository(
    private val trainingsLocalDataSource: TrainingsLocalDataSource,
    private val activeTrainingLocalDataSource: ActiveTrainingLocalDataSource,
    private val scope: CoroutineScope
) : TrainingsRepository {

    override fun observeAllTrainingBlocks(): Flow<List<TrainingBlock>> = combine(
        trainingsLocalDataSource.observeAllTrainingBlocks(),
        activeTrainingLocalDataSource.observeActiveTrainingBlockId()
    ) { trainingBlocks, activeTrainingBlockId ->
        trainingBlocks.map { it.toDomainModel(activeTrainingBlockId) }
    }

    override fun observeTrainingBlock(trainingBlockId: String): Flow<TrainingBlock?> = combine(
        trainingsLocalDataSource.observeTrainingBlock(RealmUUID.from(trainingBlockId)),
        activeTrainingLocalDataSource.observeActiveTrainingBlockId()
    ) { trainingBlock, activeTrainingBlockId ->
        trainingBlock?.toDomainModel(activeTrainingBlockId)
    }

    override suspend fun createTrainingBlock(plan: Plan, startDate: LocalDate): TrainingBlock =
        scope.async {
            val trainingBlock = TrainingBlock.from(plan, startDate)
            val trainingBlockDbModel = trainingBlock.toDbModel()
            val createdTrainingBlockDbModel = trainingsLocalDataSource.createTrainingBlock(
                trainingBlockDbModel
            )
            createdTrainingBlockDbModel.toDomainModel(activeTrainingBlockId = null)
        }.await()

    override suspend fun deleteTrainingBlock(trainingBlockId: String) = scope.async {
        trainingsLocalDataSource.deleteTrainingBlock(RealmUUID.from(trainingBlockId))
    }.await()

    override suspend fun updateProgress(
        trainingBlockId: String,
        setProgressId: String,
        newProgress: Progress
    ) = scope.async {
        trainingsLocalDataSource.updateProgress(
            trainingBlockId = RealmUUID.from(trainingBlockId),
            setProgressId = RealmUUID.from(setProgressId),
            newProgress = newProgress
        )
    }.await()

    override suspend fun setActiveTrainingBlock(trainingBlockId: String) {
        activeTrainingLocalDataSource.setActiveTrainingBlock(trainingBlockId)
    }
}
