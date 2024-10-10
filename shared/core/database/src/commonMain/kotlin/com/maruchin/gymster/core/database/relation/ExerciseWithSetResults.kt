package com.maruchin.gymster.core.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.gymster.core.database.entity.ExerciseEntity
import com.maruchin.gymster.core.database.entity.SetResultEntity

data class ExerciseWithSetResults(
    @Embedded
    val exercise: ExerciseEntity,
    @Relation(parentColumn = "id", entityColumn = "exerciseId")
    val results: List<SetResultEntity>
)
