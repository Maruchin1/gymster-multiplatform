package com.maruchin.gymster.data.trainings2.mapper

import com.maruchin.gymster.core.database2.entity.ExerciseEntity
import com.maruchin.gymster.core.database2.relation.ExerciseWithSetResults
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import com.maruchin.gymster.data.trainings2.model.Exercise

internal fun ExerciseWithSetResults.toDomainModel() = Exercise(
    id = exercise.id,
    name = exercise.name,
    sets = Sets(regular = exercise.regularSets, drop = exercise.dropSets),
    reps = Reps(exercise.minReps..exercise.maxReps),
    results = results.map { it.toDomainModel() }
)

internal fun Exercise.toEntity(trainingId: String) = ExerciseWithSetResults(
    exercise = ExerciseEntity(
        id = id,
        trainingId = trainingId,
        name = name,
        regularSets = sets.regular,
        dropSets = sets.drop,
        minReps = reps.min,
        maxReps = reps.max
    ),
    results = results.map { it.toEntity(id) }
)
