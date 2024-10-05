package com.maruchin.gymster.data.trainings.mapper

import com.maruchin.gymster.core.database.schema.TrainingDbModel
import com.maruchin.gymster.data.trainings.model.Exercise
import com.maruchin.gymster.data.trainings.model.Training
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmUUID

internal fun Training.toDbModel() = TrainingDbModel().also {
    if (id.isNotBlank()) {
        it.id = RealmUUID.from(id)
    }
    it.name = name
    it.exercises = exercises.map(Exercise::toDbModel).toRealmList()
}

internal fun TrainingDbModel.toDomainModel() = Training(
    id = id.toString(),
    name = name,
    exercises = exercises.map { it.toDomainModel() }
)
