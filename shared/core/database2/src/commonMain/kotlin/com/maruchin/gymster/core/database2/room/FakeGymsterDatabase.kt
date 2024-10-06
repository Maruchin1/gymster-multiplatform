package com.maruchin.gymster.core.database2.room

import com.maruchin.gymster.core.database2.entity.PlanEntity
import com.maruchin.gymster.core.database2.entity.PlannedExerciseEntity
import com.maruchin.gymster.core.database2.entity.PlannedTrainingEntity
import kotlinx.coroutines.flow.MutableStateFlow

internal class FakeGymsterDatabase {
    val plans = MutableStateFlow(emptyMap<Long, PlanEntity>())
    val plannedTrainings = MutableStateFlow(emptyMap<Long, PlannedTrainingEntity>())
    val plannedExercises = MutableStateFlow(emptyMap<Long, PlannedExerciseEntity>())
}
