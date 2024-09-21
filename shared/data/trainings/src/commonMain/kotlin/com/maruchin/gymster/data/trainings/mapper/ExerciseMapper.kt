package com.maruchin.gymster.data.trainings.mapper

import com.maruchin.gymster.core.database.schema.ExerciseDbModel
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import com.maruchin.gymster.data.trainings.model.Evaluation
import com.maruchin.gymster.data.trainings.model.Exercise
import com.maruchin.gymster.data.trainings.model.SetResult
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmUUID

internal fun Exercise.toDbModel() = ExerciseDbModel().also {
    it.id = id.takeIf(String::isNotBlank)?.let(RealmUUID.Companion::from) ?: RealmUUID.random()
    it.name = name
    it.regularSets = sets.regular
    it.dropSets = sets.drop
    it.minReps = reps.min
    it.maxReps = reps.max
    it.results = results.map(SetResult::toDbModel).toRealmList()
    it.evaluation = evaluation.name
}

internal fun ExerciseDbModel.toDomainModel() = Exercise(
    id = id.toString(),
    name = name,
    sets = Sets(regular = regularSets, drop = dropSets),
    reps = Reps(min = minReps, max = maxReps),
    results = results.map { it.toDomainModel() },
    evaluation = Evaluation.valueOf(evaluation)
)
