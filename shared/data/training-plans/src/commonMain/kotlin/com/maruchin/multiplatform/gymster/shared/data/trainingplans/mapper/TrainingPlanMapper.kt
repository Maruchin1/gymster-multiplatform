package com.maruchin.multiplatform.gymster.shared.data.trainingplans.mapper

import com.maruchin.multiplatform.gymster.shared.core.database.schema.TrainingPlanDbModel
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.TrainingPlan

internal fun TrainingPlanDbModel.toDomain() = TrainingPlan(
    id = id.toString(),
    name = name,
    days = days.map { it.toDomainModel() }
)
