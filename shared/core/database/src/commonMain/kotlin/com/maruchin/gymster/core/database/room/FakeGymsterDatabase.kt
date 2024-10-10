package com.maruchin.gymster.core.database.room

import com.maruchin.gymster.core.database.entity.ExerciseEntity
import com.maruchin.gymster.core.database.entity.PlanEntity
import com.maruchin.gymster.core.database.entity.PlannedExerciseEntity
import com.maruchin.gymster.core.database.entity.PlannedTrainingEntity
import com.maruchin.gymster.core.database.entity.SetResultEntity
import com.maruchin.gymster.core.database.entity.TrainingEntity
import kotlinx.coroutines.flow.MutableStateFlow

internal class FakeGymsterDatabase {
    val plans = MutableStateFlow(emptyMap<Long, PlanEntity>())
    val plannedTrainings = MutableStateFlow(emptyMap<Long, PlannedTrainingEntity>())
    val plannedExercises = MutableStateFlow(emptyMap<Long, PlannedExerciseEntity>())
    val trainings = MutableStateFlow(emptyMap<String, TrainingEntity>())
    val exercises = MutableStateFlow(emptyMap<String, ExerciseEntity>())
    val setResults = MutableStateFlow(emptyMap<String, SetResultEntity>())
}
