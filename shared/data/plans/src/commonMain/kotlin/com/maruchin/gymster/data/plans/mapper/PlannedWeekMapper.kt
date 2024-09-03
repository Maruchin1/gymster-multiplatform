package com.maruchin.gymster.data.plans.mapper

import com.maruchin.gymster.core.database.schema.PlannedTrainingDbModel
import com.maruchin.gymster.data.plans.model.PlannedWeek

internal fun Map.Entry<Int, List<PlannedTrainingDbModel>>.toDomainModel() = PlannedWeek(
    trainings = value.map { it.toDomainModel() }
)
