package com.maruchin.gymster.data.plans.mapper

import com.maruchin.gymster.core.database.schema.TrainingPlanDbModel
import com.maruchin.gymster.data.plans.model.Plan

internal fun TrainingPlanDbModel.toDomain() = Plan(
    id = id.toString(),
    name = name,
    days = days.map { it.toDomainModel() }
)
