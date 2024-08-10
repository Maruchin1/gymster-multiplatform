package com.maruchin.gymster.data.trainings.mapper

import com.maruchin.gymster.core.database.schema.TrainingBlockDbModel
import com.maruchin.gymster.data.trainings.model.TrainingBlock
import com.maruchin.gymster.data.trainings.model.TrainingWeek
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmUUID

internal fun TrainingBlock.toDbModel() = TrainingBlockDbModel().also {
    it.id = id.takeIf(String::isNotBlank)?.let(RealmUUID.Companion::from) ?: RealmUUID.random()
    it.planName = planName
    it.weeks = weeks.map(TrainingWeek::toDbModel).toRealmList()
}

internal fun TrainingBlockDbModel.toDomainModel() = TrainingBlock(
    id = id.toString(),
    planName = planName,
    weeks = weeks.map { it.toDomainModel() }
)
