package com.maruchin.gymster.core.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.gymster.core.database.entity.PlannedExerciseEntity
import com.maruchin.gymster.core.database.entity.PlannedTrainingEntity

data class PlannedTrainingWithPlannedExercises(
    @Embedded
    val training: PlannedTrainingEntity,
    @Relation(parentColumn = "id", entityColumn = "trainingId")
    val exercises: List<PlannedExerciseEntity>
)
