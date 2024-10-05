package com.maruchin.gymster.core.database2.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.gymster.core.database2.entity.TrainingEntity
import com.maruchin.gymster.core.database2.entity.TrainingWeekEntity

data class TrainingWeekWithTrainings(
    @Embedded
    val trainingWeek: TrainingWeekEntity,
    @Relation(entity = TrainingEntity::class, parentColumn = "id", entityColumn = "trainingWeekId")
    val trainings: List<TrainingWithExercises>
)
