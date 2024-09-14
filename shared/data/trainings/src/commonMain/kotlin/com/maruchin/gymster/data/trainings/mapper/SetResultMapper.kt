package com.maruchin.gymster.data.trainings.mapper

import com.maruchin.gymster.core.database.schema.SetResultDbModel
import com.maruchin.gymster.data.trainings.model.SetResult
import io.realm.kotlin.types.RealmUUID

internal fun SetResult.toDbModel() = SetResultDbModel().also {
    if (id.isNotBlank()) {
        it.id = RealmUUID.random()
    }
    it.type = type.name
    it.weight = weight
    it.reps = reps
}

internal fun SetResultDbModel.toDomainModel() = SetResult(
    id = id.toString(),
    type = SetResult.Type.valueOf(type),
    weight = weight,
    reps = reps
)
