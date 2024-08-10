package com.maruchin.gymster.core.database.schema

internal val databaseSchema = setOf(
    PlanDbModel::class,
    PlannedTrainingDbModel::class,
    PlannedExerciseDbModel::class,
    TrainingBlockDbModel::class,
    TrainingWeekDbModel::class,
    TrainingDbModel::class,
    ExerciseDbModel::class,
    ProgressDbModel::class
)
