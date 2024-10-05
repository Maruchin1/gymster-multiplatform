package com.maruchin.gymster.core.database2.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.gymster.core.database2.entity.PlannedExerciseEntity
import com.maruchin.gymster.core.database2.entity.PlannedTrainingEntity

data class PlannedTrainingWithPlannedExercises(
    @Embedded
    val training: PlannedTrainingEntity,
    @Relation(parentColumn = "id", entityColumn = "trainingId")
    val exercises: List<PlannedExerciseEntity>
)
