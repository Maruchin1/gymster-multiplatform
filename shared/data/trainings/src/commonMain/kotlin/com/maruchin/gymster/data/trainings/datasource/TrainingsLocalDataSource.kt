package com.maruchin.gymster.data.trainings.datasource

import com.maruchin.gymster.core.database.schema.TrainingBlockDbModel
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class TrainingsLocalDataSource(private val realm: Realm) {

    fun observeAllTrainingBlocks(): Flow<List<TrainingBlockDbModel>> =
        realm.query<TrainingBlockDbModel>().asFlow().map { change ->
            change.list.toList()
        }

    fun observeTrainingBlock(trainingBlockId: RealmUUID): Flow<TrainingBlockDbModel?> =
        realm.query<TrainingBlockDbModel>("_id == $0", trainingBlockId).asFlow().map { change ->
            change.list.firstOrNull()
        }

    suspend fun createTrainingBlock(trainingBlock: TrainingBlockDbModel): TrainingBlockDbModel =
        realm.write {
            copyToRealm(trainingBlock)
        }

    suspend fun deleteTrainingBlock(trainingBlockId: RealmUUID) {
        realm.write {
            val trainingBlock =
                query<TrainingBlockDbModel>("_id == $0", trainingBlockId).find().first()
            delete(trainingBlock)
        }
    }

    suspend fun updateSetResultWeight(
        trainingBlockId: RealmUUID,
        weekIndex: Int,
        trainingIndex: Int,
        exerciseIndex: Int,
        setIndex: Int,
        weight: Double?
    ) {
        realm.write {
            val trainingBlock =
                query<TrainingBlockDbModel>("_id == $0", trainingBlockId).find().first()
            val setResult = trainingBlock.trainings
                .filter { it.week == weekIndex }[trainingIndex]
                .exercises[exerciseIndex]
                .results[setIndex]
            setResult.weight = weight
        }
    }

    suspend fun updateSetResultReps(
        trainingBlockId: RealmUUID,
        weekIndex: Int,
        trainingIndex: Int,
        exerciseIndex: Int,
        setIndex: Int,
        reps: Int?
    ) {
        realm.write {
            val trainingBlock =
                query<TrainingBlockDbModel>("_id == $0", trainingBlockId).find().first()
            val setResult = trainingBlock.trainings
                .filter { it.week == weekIndex }[trainingIndex]
                .exercises[exerciseIndex]
                .results[setIndex]
            setResult.reps = reps
        }
    }
}
