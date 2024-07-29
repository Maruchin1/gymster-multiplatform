package com.maruchin.gymster.data.plans.mapper

import com.maruchin.gymster.core.database.schema.TrainingPlanExerciseDbModel
import com.maruchin.gymster.data.plans.model.PlanExercise
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets

internal fun TrainingPlanExerciseDbModel.toDomainModel() = PlanExercise(
    id = id.toString(),
    name = name,
    sets = Sets(regular = regularSets, drop = dropSets),
    reps = Reps(min = minReps, max = maxReps)
)
