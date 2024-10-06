package com.maruchin.gymster.data.plans.mapper

import com.maruchin.gymster.core.database2.relation.PlanWithPlannedTrainings
import com.maruchin.gymster.data.plans.model.Plan

internal fun PlanWithPlannedTrainings.toDomainModel() = Plan(
    id = plan.id.toString(),
    name = plan.name,
    trainings = trainings.map { it.toDomainModel() }
)
