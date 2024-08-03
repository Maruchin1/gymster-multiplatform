package com.maruchin.gymster.data.trainings.mapper

import com.maruchin.gymster.core.database.schema.ExerciseDbModel
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import com.maruchin.gymster.data.trainings.model.Exercise
import com.maruchin.gymster.data.trainings.model.Progress
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmUUID

internal fun Exercise.toDbModel() = ExerciseDbModel().also {
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

internal fun ExerciseDbModel.toDomainModel() = Exercise(
    id = id.toString(),
    name = name,
    sets = Sets(regular = regularSets, drop = dropSets),
    reps = Reps(min = minReps, max = maxReps),
    progress = progress.map { it.toDomainModel() }
)
