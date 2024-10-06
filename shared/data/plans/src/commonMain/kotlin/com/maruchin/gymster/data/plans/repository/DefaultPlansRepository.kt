package com.maruchin.gymster.data.plans.repository

import com.maruchin.gymster.core.database2.relation.PlanWithPlannedTrainings
import com.maruchin.gymster.data.plans.datasource.PlansLocalDataSource
import com.maruchin.gymster.data.plans.mapper.toDomainModel
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DefaultPlansRepository(private val plansLocalDataSource: PlansLocalDataSource) :
    PlansRepository {

    override fun observeAllPlans(): Flow<List<Plan>> = plansLocalDataSource.observeAllPlans().map {
        it.map(PlanWithPlannedTrainings::toDomainModel)
    }

    override fun observePlan(planId: String): Flow<Plan?> =
        plansLocalDataSource.observePlan(planId.toLong()).map { it?.toDomainModel() }

    override suspend fun createPlan(name: String): String =
        plansLocalDataSource.createPlan(name).toString()

    override suspend fun updatePlan(planId: String, name: String) {
        plansLocalDataSource.updatePlan(planId = planId.toLong(), name = name)
    }

    override suspend fun deletePlan(planId: String) {
        plansLocalDataSource.deletePlan(planId.toLong())
    }

    override suspend fun addTraining(planId: String, name: String): String =
        plansLocalDataSource.addTraining(planId = planId.toLong(), name = name).toString()

    override suspend fun updateTraining(trainingId: String, name: String) {
        plansLocalDataSource.updateTraining(trainingId = trainingId.toLong(), name = name)
    }

    override suspend fun deleteTraining(trainingId: String) {
        plansLocalDataSource.deleteTraining(trainingId = trainingId.toLong())
    }

    override suspend fun addExercise(
        trainingId: String,
        name: String,
        sets: Sets,
        reps: Reps
    ): String = plansLocalDataSource.addExercise(
        trainingId = trainingId.toLong(),
        name = name,
        sets = sets,
        reps = reps
    ).toString()

    override suspend fun updateExercise(exerciseId: String, name: String, sets: Sets, reps: Reps) {
        plansLocalDataSource.updateExercise(
            exerciseId = exerciseId.toLong(),
            name = name,
            sets = sets,
            reps = reps
        )
    }

    override suspend fun deleteExercise(exerciseId: String) {
        plansLocalDataSource.deleteExercise(exerciseId.toLong())
    }

    override suspend fun reorderExercises(exercisesIds: List<String>) {
        plansLocalDataSource.reorderExercises(exercisesIds.map { it.toLong() })
    }
}
