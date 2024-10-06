package com.maruchin.gymster.data.plans.datasource

import com.maruchin.gymster.core.database2.dao.PlanDao
import com.maruchin.gymster.core.database2.dao.PlannedExerciseDao
import com.maruchin.gymster.core.database2.dao.PlannedTrainingDao
import com.maruchin.gymster.core.database2.entity.PlanEntity
import com.maruchin.gymster.core.database2.entity.PlannedExerciseEntity
import com.maruchin.gymster.core.database2.entity.PlannedTrainingEntity
import com.maruchin.gymster.core.database2.relation.PlanWithPlannedTrainings
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import kotlinx.coroutines.flow.Flow

internal class PlansLocalDataSource(
    private val planDao: PlanDao,
    private val plannedTrainingDao: PlannedTrainingDao,
    private val plannedExerciseDao: PlannedExerciseDao
) {

    fun observeAllPlans(): Flow<List<PlanWithPlannedTrainings>> =
        planDao.observeAllPlansWithPlannedTrainings()

    fun observePlan(planId: Long): Flow<PlanWithPlannedTrainings?> =
        planDao.observePlanWithPlannedTrainings(planId)

    suspend fun createPlan(name: String): Long {
        val plan = PlanEntity(id = 0, name = name)
        return planDao.insertPlan(plan)
    }

    suspend fun updatePlan(planId: Long, name: String) {
        val plan = planDao.getPlan(planId)
        checkNotNull(plan)
        val updatedPlan = plan.copy(name = name)
        planDao.updatePlan(updatedPlan)
    }

    suspend fun deletePlan(planId: Long) {
        val plan = planDao.getPlan(planId)
        checkNotNull(plan)
        planDao.deletePlan(plan)
    }

    suspend fun addTraining(planId: Long, name: String): Long {
        val training = PlannedTrainingEntity(id = 0, planId = planId, name = name)
        return plannedTrainingDao.insertPlannedTraining(training)
    }

    suspend fun updateTraining(trainingId: Long, name: String) {
        val training = plannedTrainingDao.getPlannedTraining(trainingId)
        checkNotNull(training)
        val updateTraining = training.copy(name = name)
        plannedTrainingDao.updatePlannedTraining(updateTraining)
    }

    suspend fun deleteTraining(trainingId: Long) {
        val training = plannedTrainingDao.getPlannedTraining(trainingId)
        checkNotNull(training)
        plannedTrainingDao.deletePlannedTraining(training)
    }

    suspend fun addExercise(trainingId: Long, name: String, sets: Sets, reps: Reps): Long {
        val numOfExercises = plannedExerciseDao.countPlannedExercisesForTraining(trainingId)
        val exercise = PlannedExerciseEntity(
            id = 0,
            trainingId = trainingId,
            index = numOfExercises,
            name = name,
            regularSets = sets.regular,
            dropSets = sets.drop,
            minReps = reps.min,
            maxReps = reps.max
        )
        return plannedExerciseDao.insertPlannedExercise(exercise)
    }

    suspend fun updateExercise(exerciseId: Long, name: String, sets: Sets, reps: Reps) {
        val exercise = plannedExerciseDao.getPlannedExercise(exerciseId)
        checkNotNull(exercise)
        val updatedExercise = exercise.copy(
            name = name,
            regularSets = sets.regular,
            dropSets = sets.drop,
            minReps = reps.min,
            maxReps = reps.max
        )
        plannedExerciseDao.updatePlannedExercise(updatedExercise)
    }

    suspend fun deleteExercise(exerciseId: Long) {
        val exercise = plannedExerciseDao.getPlannedExercise(exerciseId)
        checkNotNull(exercise)
        plannedExerciseDao.deletePlannedExercise(exercise)
    }

    suspend fun reorderExercises(exercisesIds: List<Long>) {
        exercisesIds.forEachIndexed { index, exerciseId ->
            val exercise = plannedExerciseDao.getPlannedExercise(exerciseId)
            checkNotNull(exercise)
            val updatedExercise = exercise.copy(index = index)
            plannedExerciseDao.updatePlannedExercise(updatedExercise)
        }
    }
}
