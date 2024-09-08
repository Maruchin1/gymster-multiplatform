package com.maruchin.gymster.data.plans.mapper

import com.maruchin.gymster.core.database.schema.PlanDbModel
import com.maruchin.gymster.data.plans.model.Plan

internal fun PlanDbModel.toDomainModel() = Plan(
    id = id.toString(),
    name = name,
    trainings = trainings.map { it.toDomainModel() }
)
