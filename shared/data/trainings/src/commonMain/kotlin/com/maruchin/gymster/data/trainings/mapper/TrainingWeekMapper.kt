package com.maruchin.gymster.data.trainings.mapper

import com.maruchin.gymster.core.database.schema.TrainingDbModel
import com.maruchin.gymster.core.database.schema.TrainingWeekDbModel
import com.maruchin.gymster.data.trainings.model.Training
import com.maruchin.gymster.data.trainings.model.TrainingWeek
import io.realm.kotlin.ext.toRealmList

internal fun TrainingWeek.toDbModel() = TrainingWeekDbModel().also {
    it.isComplete
    it.trainings = trainings.map(Training::toDbModel).toRealmList()
}

internal fun TrainingWeekDbModel.toDomainModel() = TrainingWeek(
    isComplete = isComplete,
    trainings = trainings.map(TrainingDbModel::toDomainModel)
)
