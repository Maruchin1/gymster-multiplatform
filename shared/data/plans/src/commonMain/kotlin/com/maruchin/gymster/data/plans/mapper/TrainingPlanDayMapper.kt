package com.maruchin.gymster.data.plans.mapper

import com.maruchin.gymster.core.database.schema.TrainingPlanDayDbModel
import com.maruchin.gymster.data.plans.model.PlanDay

internal fun TrainingPlanDayDbModel.toDomainModel() = PlanDay(
    id = id.toString(),
    name = name,
    exercises = exercises.map { it.toDomainModel() }
)
