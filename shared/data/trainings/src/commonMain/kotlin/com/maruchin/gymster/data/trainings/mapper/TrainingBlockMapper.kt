package com.maruchin.gymster.data.trainings.mapper

import com.maruchin.gymster.core.database.schema.TrainingBlockDbModel
import com.maruchin.gymster.core.database.schema.TrainingWeekDbModel
import com.maruchin.gymster.data.trainings.model.TrainingBlock
import com.maruchin.gymster.data.trainings.model.TrainingWeek
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmUUID
import kotlinx.datetime.LocalDate

internal fun TrainingBlock.toDbModel() = TrainingBlockDbModel().also {
    if (id.isNotBlank()) {
        it.id = RealmUUID.from(id)
    }
    it.planName = planName
    it.startDate = startDate.toString()
    it.weeks = weeks.map(TrainingWeek::toDbModel).toRealmList()
}

internal fun TrainingBlockDbModel.toDomainModel(activeTrainingBlockId: String?) = TrainingBlock(
    id = id.toString(),
    planName = planName,
    startDate = LocalDate.parse(startDate),
    weeks = weeks.map(TrainingWeekDbModel::toDomainModel),
    isActive = id.toString() == activeTrainingBlockId
)
