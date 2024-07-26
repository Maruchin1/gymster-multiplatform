package com.maruchin.multiplatform.gymster.data.trainingplans.mapper

import com.maruchin.multiplatform.gymster.core.database.schema.TrainingPlanExerciseDbModel
import com.maruchin.multiplatform.gymster.data.trainingplans.model.Reps
import com.maruchin.multiplatform.gymster.data.trainingplans.model.Sets
import com.maruchin.multiplatform.gymster.data.trainingplans.model.TrainingPlanExercise

internal fun TrainingPlanExerciseDbModel.toDomainModel() = TrainingPlanExercise(
    id = id.toString(),
    name = name,
    sets = Sets(regular = regularSets, drop = dropSets),
    reps = Reps(min = minReps, max = maxReps)
)
