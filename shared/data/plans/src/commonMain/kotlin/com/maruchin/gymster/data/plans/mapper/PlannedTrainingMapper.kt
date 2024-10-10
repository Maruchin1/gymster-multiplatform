package com.maruchin.gymster.data.plans.mapper

import com.maruchin.gymster.core.database.relation.PlannedTrainingWithPlannedExercises
import com.maruchin.gymster.data.plans.model.PlannedTraining

internal fun PlannedTrainingWithPlannedExercises.toDomainModel() = PlannedTraining(
    id = training.id.toString(),
    name = training.name,
    exercises = exercises.sortedBy { it.index }.map { it.toDomainModel() }
)
