package com.maruchin.multiplatform.gymster.data.trainingplans.repository

import com.maruchin.multiplatform.gymster.data.trainingplans.model.Reps
import com.maruchin.multiplatform.gymster.data.trainingplans.model.Sets
import com.maruchin.multiplatform.gymster.data.trainingplans.model.TrainingPlan
import kotlinx.coroutines.flow.Flow

internal interface TrainingPlansRepository {

    fun observeAllPlans(): Flow<List<TrainingPlan>>

    fun observePlan(planId: String): Flow<TrainingPlan?>

    suspend fun createPlan(name: String): String

    suspend fun changePlanName(planId: String, newName: String)

    suspend fun deletePlan(planId: String)

    suspend fun addDay(planId: String, name: String): String

    suspend fun changeDayName(planId: String, dayId: String, newName: String)

    suspend fun deleteDay(planId: String, dayId: String)

    suspend fun addExercise(
        planId: String,
        dayId: String,
        name: String,
        sets: Sets,
        reps: Reps
    ): String

    suspend fun updateExercise(
        planId: String,
        dayId: String,
        exerciseId: String,
        newName: String,
        newSets: Sets,
        newReps: Reps
    )

    suspend fun deleteExercise(planId: String, dayId: String, exerciseId: String)
}
