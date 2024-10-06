package com.maruchin.gymster.data.plans.repository

import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import kotlinx.coroutines.flow.Flow

interface PlansRepository2 {

    fun observeAllPlans(): Flow<List<Plan>>

    fun observePlan(planId: String): Flow<Plan?>

    suspend fun createPlan(name: String): String

    suspend fun updatePlan(planId: String, name: String)

    suspend fun deletePlan(planId: String)

    suspend fun addTraining(planId: String, name: String): String

    suspend fun updateTraining(trainingId: String, name: String)

    suspend fun deleteTraining(trainingId: String)

    suspend fun addExercise(trainingId: String, name: String, sets: Sets, reps: Reps): String

    suspend fun updateExercise(exerciseId: String, name: String, sets: Sets, reps: Reps)

    suspend fun deleteExercise(exerciseId: String)

    suspend fun reorderExercises(exercisesIds: List<String>)
}
