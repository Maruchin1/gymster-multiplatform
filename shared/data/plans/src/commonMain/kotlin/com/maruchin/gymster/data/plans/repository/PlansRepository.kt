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

    suspend fun addTraining(planId: String, name: String): PlannedTraining

    suspend fun changeTrainingName(planId: String, trainingIndex: Int, newName: String)

    suspend fun deleteTraining(planId: String, trainingIndex: Int)

    suspend fun addExercise(
        planId: String,
        trainingIndex: Int,
        name: String,
        sets: Sets,
        reps: Reps
    ): PlannedExercise

    suspend fun updateExercise(
        planId: String,
        trainingIndex: Int,
        exerciseIndex: Int,
        newName: String,
        newSets: Sets,
        newReps: Reps
    )

    suspend fun deleteExercise(planId: String, trainingIndex: Int, exerciseIndex: Int)

    suspend fun reorderExercises(planId: String, trainingIndex: Int, exercisesIds: List<String>)
}
