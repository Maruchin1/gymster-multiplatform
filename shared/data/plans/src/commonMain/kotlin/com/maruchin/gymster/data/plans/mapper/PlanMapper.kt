package com.maruchin.gymster.data.plans.mapper

import com.maruchin.gymster.core.database.schema.PlanDbModel
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.PlannedWeek

internal fun PlanDbModel.toDomainModel() = Plan(
    id = id.toString(),
    name = name,
    weeks = if (trainings.isNotEmpty()) {
        trainings.sortedBy { it.weekIndex }.groupBy { it.weekIndex }.map { it.toDomainModel() }
    } else {
        val firstWeek = PlannedWeek(trainings = emptyList())
        listOf(firstWeek)
    }
)
