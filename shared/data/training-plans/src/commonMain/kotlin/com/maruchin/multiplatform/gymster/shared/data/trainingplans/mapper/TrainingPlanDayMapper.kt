package com.maruchin.multiplatform.gymster.shared.data.trainingplans.mapper

import com.maruchin.multiplatform.gymster.shared.core.database.schema.TrainingPlanDayDbModel
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.TrainingPlanDay

internal fun TrainingPlanDayDbModel.toDomainModel() = TrainingPlanDay(
    id = id.toString(),
    name = name,
    exercises = exercises.map { it.toDomainModel() }
)
