package com.maruchin.gymster.core.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.maruchin.gymster.core.database.entity.PlanEntity
import com.maruchin.gymster.core.database.entity.PlannedTrainingEntity

data class PlanWithPlannedTrainings(
    @Embedded
    val plan: PlanEntity,
    @Relation(parentColumn = "id", entityColumn = "planId", entity = PlannedTrainingEntity::class)
    val trainings: List<PlannedTrainingWithPlannedExercises>
)
