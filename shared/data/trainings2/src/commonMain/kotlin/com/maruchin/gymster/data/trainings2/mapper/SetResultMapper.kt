package com.maruchin.gymster.data.trainings2.mapper

import com.maruchin.gymster.core.database2.entity.SetResultEntity
import com.maruchin.gymster.data.trainings2.model.SetResult

internal fun SetResultEntity.toDomainModel() = SetResult(
    id = id,
    type = SetResult.Type.valueOf(type),
    weight = weight,
    reps = reps
)

internal fun SetResult.toEntity(exerciseId: String) = SetResultEntity(
    id = id,
    exerciseId = exerciseId,
    type = type.name,
    weight = weight,
    reps = reps
)
