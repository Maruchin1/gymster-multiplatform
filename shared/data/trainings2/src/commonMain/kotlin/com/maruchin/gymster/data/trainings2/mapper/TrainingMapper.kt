package com.maruchin.gymster.data.trainings2.mapper

import com.maruchin.gymster.core.database2.entity.TrainingEntity
import com.maruchin.gymster.core.database2.relation.TrainingWithExercises
import com.maruchin.gymster.data.trainings2.model.Training

internal fun TrainingWithExercises.toDomainModel() = Training(
    id = training.id,
    name = training.name,
    planName = training.planName,
    date = training.date,
    isComplete = training.isComplete,
    exercises = exercises.map { it.toDomainModel() }
)

internal fun Training.toEntity() = TrainingWithExercises(
    training = TrainingEntity(
        id = id,
        name = name,
        planName = planName,
        date = date,
        isComplete = isComplete
    ),
    exercises = exercises.map { it.toEntity(id) }
)
