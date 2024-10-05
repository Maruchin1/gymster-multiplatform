package com.maruchin.gymster.core.database2.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.gymster.core.database2.entity.ExerciseEntity
import com.maruchin.gymster.core.database2.entity.SetResultEntity

data class ExerciseWithSetResults(
    @Embedded
    val exercise: ExerciseEntity,
    @Relation(parentColumn = "id", entityColumn = "exerciseId")
    val results: List<SetResultEntity>
)
