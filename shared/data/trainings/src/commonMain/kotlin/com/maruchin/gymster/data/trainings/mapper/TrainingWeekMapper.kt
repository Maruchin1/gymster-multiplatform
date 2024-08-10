package com.maruchin.gymster.data.trainings.mapper

import com.maruchin.gymster.core.database.schema.TrainingWeekDbModel
import com.maruchin.gymster.data.trainings.model.Training
import com.maruchin.gymster.data.trainings.model.TrainingWeek
import io.realm.kotlin.ext.toRealmList

internal fun TrainingWeek.toDbModel() = TrainingWeekDbModel().also {
    it.number = number
    it.trainings = trainings.map(Training::toDbModel).toRealmList()
}

internal fun TrainingWeekDbModel.toDomainModel() = TrainingWeek(
    number = number,
    trainings = trainings.map { it.toDomainModel() }
)
