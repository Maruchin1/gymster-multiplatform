package com.maruchin.gymster.data.plans.repository

import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.PlannedExercise
import com.maruchin.gymster.data.plans.model.PlannedTraining
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import kotlinx.coroutines.flow.Flow

interface PlansRepository {

    fun observeAllPlans(): Flow<List<Plan>>

    fun observePlan(planId: String): Flow<Plan?>

    suspend fun createPlan(name: String): Plan

    suspend fun changePlanName(planId: String, newName: String)

    suspend fun deletePlan(planId: String)

    suspend fun addWeek(planId: String): List<PlannedTraining>

    suspend fun addTraining(planId: String, weekIndex: Int, name: String): PlannedTraining

    suspend fun changeTrainingName(planId: String, trainingId: String, newName: String)

    suspend fun deleteTraining(planId: String, trainingId: String)

    suspend fun addExercise(
        planId: String,
        trainingId: String,
        name: String,
        sets: Sets,
        reps: Reps
    ): PlannedExercise

    suspend fun updateExercise(
        planId: String,
        trainingId: String,
        exerciseId: String,
        newName: String,
        newSets: Sets,
        newReps: Reps
    )

    suspend fun deleteExercise(planId: String, trainingId: String, exerciseId: String)

    suspend fun reorderExercises(planId: String, trainingId: String, exercisesIds: List<String>)
}
