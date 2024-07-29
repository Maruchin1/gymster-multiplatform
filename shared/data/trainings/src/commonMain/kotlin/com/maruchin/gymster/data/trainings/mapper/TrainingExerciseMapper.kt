package com.maruchin.gymster.data.trainings.mapper

import com.maruchin.gymster.core.database.schema.TrainingExerciseDbModel
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.TrainingExercise
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmUUID

internal fun TrainingExercise.toDbModel() = TrainingExerciseDbModel().also {
    if (id.isNotBlank()) {
        it.id = RealmUUID.from(id)
    }
    it.name = name
    it.regularSets = sets.regular
    it.dropSets = sets.drop
    it.minReps = reps.min
    it.maxReps = reps.max
    it.progress = progress.map(Progress::toDbModel).toRealmList()
}

internal fun TrainingExerciseDbModel.toDomainModel() = TrainingExercise(
    id = id.toString(),
    name = name,
    sets = Sets(regular = regularSets, drop = dropSets),
    reps = Reps(min = minReps, max = maxReps),
    progress = progress.map { it.toDomainModel() }
)
