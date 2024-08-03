package com.maruchin.gymster.data.plans.mapper

import com.maruchin.gymster.core.database.schema.PlannedTrainingDbModel
import com.maruchin.gymster.data.plans.model.PlannedTraining

internal fun PlannedTrainingDbModel.toDomainModel() = PlannedTraining(
    id = id.toString(),
    name = name,
    exercises = exercises.map { it.toDomainModel() }
)
