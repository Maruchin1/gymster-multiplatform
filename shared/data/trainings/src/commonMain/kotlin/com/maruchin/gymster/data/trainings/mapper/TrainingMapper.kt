package com.maruchin.gymster.data.trainings.mapper

import com.maruchin.gymster.core.database.schema.TrainingDbModel
import com.maruchin.gymster.data.trainings.model.Training
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmUUID
import kotlinx.datetime.LocalDate

internal fun Training.toDbModel() = TrainingDbModel().also {
    if (id.isNotBlank()) {
        it.id = RealmUUID.from(id)
    }
    it.date = date.toString()
    it.exercises = exercises.map { it.toDbModel() }.toRealmList()
}

internal fun TrainingDbModel.toDomainModel() = Training(
    id = id.toString(),
    date = LocalDate.parse(date),
    exercises = exercises.map { it.toDomainModel() }
)
