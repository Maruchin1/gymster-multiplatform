package com.maruchin.gymster.core.database2.room

import com.maruchin.gymster.core.database2.entity.ExerciseEntity
import com.maruchin.gymster.core.database2.entity.PlanEntity
import com.maruchin.gymster.core.database2.entity.PlannedExerciseEntity
import com.maruchin.gymster.core.database2.entity.PlannedTrainingEntity
import com.maruchin.gymster.core.database2.entity.SetResultEntity
import com.maruchin.gymster.core.database2.entity.TrainingEntity
import kotlinx.coroutines.flow.MutableStateFlow

internal class FakeGymsterDatabase {
    val plans = MutableStateFlow(emptyMap<Long, PlanEntity>())
    val plannedTrainings = MutableStateFlow(emptyMap<Long, PlannedTrainingEntity>())
    val plannedExercises = MutableStateFlow(emptyMap<Long, PlannedExerciseEntity>())
    val trainings = MutableStateFlow(emptyMap<String, TrainingEntity>())
    val exercises = MutableStateFlow(emptyMap<String, ExerciseEntity>())
    val setResults = MutableStateFlow(emptyMap<String, SetResultEntity>())
}
