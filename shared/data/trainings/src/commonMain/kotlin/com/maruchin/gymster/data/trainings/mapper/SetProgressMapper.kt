package com.maruchin.gymster.data.trainings.mapper

import com.maruchin.gymster.core.database.schema.SetProgressDbModel
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.SetProgress
import io.realm.kotlin.types.RealmUUID

internal fun SetProgress.toDbModel() = SetProgressDbModel().also {
    if (id.isNotBlank()) {
        it.id = RealmUUID.from(id)
    }
    it.type = type.name
    it.weight = progress?.weight
    it.reps = progress?.reps
}

internal fun SetProgressDbModel.toDomainModel() = SetProgress(
    id = id.toString(),
    type = SetProgress.Type.valueOf(type),
    progress = weight?.let { weight ->
        reps?.let { reps ->
            Progress(weight, reps)
        }
    }
)
