package com.maruchin.gymster.data.trainings.datasource

import com.maruchin.gymster.core.database.schema.ProgressDbModel
import com.maruchin.gymster.core.database.schema.TrainingDbModel
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class TrainingsLocalDataSource(private val realm: Realm) {

    fun observeAllTrainings(): Flow<List<TrainingDbModel>> =
        realm.query<TrainingDbModel>().asFlow().map { change ->
            change.list.toList()
        }

    fun observeTraining(trainingId: RealmUUID): Flow<TrainingDbModel?> =
        realm.query<TrainingDbModel>("_id == $0", trainingId).asFlow().map { change ->
            change.list.firstOrNull()
        }

    suspend fun createTraining(training: TrainingDbModel) {
        realm.write {
            copyToRealm(training)
        }
    }

    suspend fun updateProgress(
        trainingId: RealmUUID,
        exerciseId: RealmUUID,
        progressIndex: Int,
        newProgress: ProgressDbModel
    ) {
        realm.write {
            val training = query<TrainingDbModel>("_id == $0", trainingId).find().first()
            val exercise = training.exercises.find { it.id == exerciseId }!!
            exercise.progress[progressIndex] = newProgress
        }
    }
}
