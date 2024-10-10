package com.maruchin.gymster.core.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.gymster.core.database.entity.ExerciseEntity
import com.maruchin.gymster.core.database.entity.TrainingEntity

data class TrainingWithExercises(
    @Embedded
    val training: TrainingEntity,
    @Relation(entity = ExerciseEntity::class, parentColumn = "id", entityColumn = "trainingId")
    val exercises: List<ExerciseWithSetResults>
)
