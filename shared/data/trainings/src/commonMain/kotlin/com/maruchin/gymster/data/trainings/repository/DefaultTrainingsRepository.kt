package com.maruchin.gymster.data.trainings.repository

import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.trainings.datasource.ActiveTrainingLocalDataSource
import com.maruchin.gymster.data.trainings.datasource.TrainingsLocalDataSource
import com.maruchin.gymster.data.trainings.mapper.toDbModel
import com.maruchin.gymster.data.trainings.mapper.toDomainModel
import com.maruchin.gymster.data.trainings.model.TrainingBlock
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.LocalDate

internal class DefaultTrainingsRepository(
    private val trainingsLocalDataSource: TrainingsLocalDataSource,
    private val activeTrainingLocalDataSource: ActiveTrainingLocalDataSource
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

    override suspend fun createTrainingBlock(
        plan: Plan,
        startDate: LocalDate,
        weeksDuration: Int
    ): TrainingBlock {
        val trainingBlock = TrainingBlock.from(plan, startDate, weeksDuration)
        val trainingBlockDbModel = trainingBlock.toDbModel()
        val createdTrainingBlockDbModel = trainingsLocalDataSource.createTrainingBlock(
            trainingBlockDbModel
        )
        return createdTrainingBlockDbModel.toDomainModel(activeTrainingBlockId = null)
    }

    override suspend fun deleteTrainingBlock(trainingBlockId: String) {
        trainingsLocalDataSource.deleteTrainingBlock(RealmUUID.from(trainingBlockId))
    }

    override suspend fun updateSetResultWeight(
        trainingBlockId: String,
        setResultId: String,
        weight: Double?
    ) {
        trainingsLocalDataSource.updateSetResultWeight(
            trainingBlockId = RealmUUID.from(trainingBlockId),
            setResultId = RealmUUID.from(setResultId),
            weight = weight
        )
    }

    override suspend fun updateSetResultReps(
        trainingBlockId: String,
        setResultId: String,
        reps: Int?
    ) {
        trainingsLocalDataSource.updateSetResultReps(
            trainingBlockId = RealmUUID.from(trainingBlockId),
            setResultId = RealmUUID.from(setResultId),
            reps = reps
        )
    }

    override suspend fun setActiveTrainingBlock(trainingBlockId: String) {
        activeTrainingLocalDataSource.setActiveTrainingBlock(trainingBlockId)
    }
}
