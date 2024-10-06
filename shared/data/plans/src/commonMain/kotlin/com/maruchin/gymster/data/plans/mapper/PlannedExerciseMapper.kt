package com.maruchin.gymster.data.plans.mapper

import com.maruchin.gymster.core.database2.entity.PlannedExerciseEntity
import com.maruchin.gymster.data.plans.model.PlannedExercise
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets

internal fun PlannedExerciseEntity.toDomainModel() = PlannedExercise(
    id = id.toString(),
    name = name,
    sets = Sets(regular = regularSets, drop = dropSets),
    reps = Reps(min = minReps, max = maxReps)
)
