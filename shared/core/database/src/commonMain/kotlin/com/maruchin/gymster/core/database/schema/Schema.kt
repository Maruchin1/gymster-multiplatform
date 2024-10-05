package com.maruchin.gymster.core.database.schema

// TODO Migrate to Room as Realm is deprecated

internal val databaseSchema = setOf(
    PlanDbModel::class,
    PlannedTrainingDbModel::class,
    PlannedExerciseDbModel::class,
    TrainingBlockDbModel::class,
    TrainingWeekDbModel::class,
    TrainingDbModel::class,
    ExerciseDbModel::class,
    SetResultDbModel::class
)
