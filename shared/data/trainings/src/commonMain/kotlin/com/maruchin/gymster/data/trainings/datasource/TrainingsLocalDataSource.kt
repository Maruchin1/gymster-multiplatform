package com.maruchin.gymster.data.trainings.datasource

import com.maruchin.gymster.core.database.schema.ProgressDbModel
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

    suspend fun updateProgress(
        trainingBlockId: RealmUUID,
        weekIndex: Int,
        trainingId: RealmUUID,
        exerciseId: RealmUUID,
        progressIndex: Int,
        newProgress: ProgressDbModel
    ) {
        realm.write {
            val trainingBlock =
                query<TrainingBlockDbModel>("_id == $0", trainingBlockId).find().first()
            val trainingWeek = trainingBlock.weeks[weekIndex]
            val training = trainingWeek.trainings.first { it.id == trainingId }
            val exercise = training.exercises.first { it.id == exerciseId }
            exercise.progress[progressIndex] = newProgress
        }
    }
}
