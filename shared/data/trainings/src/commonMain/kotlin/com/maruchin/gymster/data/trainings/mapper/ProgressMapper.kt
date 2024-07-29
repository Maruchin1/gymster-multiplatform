package com.maruchin.gymster.data.trainings.mapper

import com.maruchin.gymster.core.database.schema.ProgressDbModel
import com.maruchin.gymster.data.trainings.model.Progress

internal fun Progress.toDbModel() = ProgressDbModel().also {
    it.weight = weight
    it.reps = reps
}

internal fun ProgressDbModel.toDomainModel() = Progress(
    weight = weight,
    reps = reps
)
