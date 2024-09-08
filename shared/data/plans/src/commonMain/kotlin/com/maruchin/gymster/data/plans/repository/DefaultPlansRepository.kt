package com.maruchin.gymster.data.plans.repository

import com.maruchin.gymster.core.database.schema.PlanDbModel
import com.maruchin.gymster.data.plans.datasource.PlansLocalDataSource
import com.maruchin.gymster.data.plans.mapper.toDomainModel
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.PlannedExercise
import com.maruchin.gymster.data.plans.model.PlannedTraining
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DefaultPlansRepository(private val localDataSource: PlansLocalDataSource) :
    PlansRepository {

    override fun observeAllPlans(): Flow<List<Plan>> = localDataSource.observeAllPlans().map {
        it.map(PlanDbModel::toDomainModel)
    }

    override fun observePlan(planId: String): Flow<Plan?> =
        localDataSource.observePlan(planId = RealmUUID.from(planId)).map {
            it?.toDomainModel()
        }

    override suspend fun createPlan(name: String): Plan {
        val planDbModel = localDataSource.createPlan(name = name)
        return planDbModel.toDomainModel()
    }

    override suspend fun changePlanName(planId: String, newName: String) {
        localDataSource.updatePlan(planId = RealmUUID.from(planId), newName = newName)
    }

    override suspend fun deletePlan(planId: String) {
        localDataSource.deletePlan(planId = RealmUUID.from(planId))
    }

    override suspend fun addTraining(planId: String, name: String): PlannedTraining {
        val plannedTrainingDbModel = localDataSource.addTraining(
            planId = RealmUUID.from(planId),
            name = name
        )
        return plannedTrainingDbModel.toDomainModel()
    }

    override suspend fun changeTrainingName(planId: String, trainingId: String, newName: String) {
        localDataSource.updateTraining(
            planId = RealmUUID.from(planId),
            dayId = RealmUUID.from(trainingId),
            name = newName
        )
    }

    override suspend fun deleteTraining(planId: String, trainingId: String) {
        localDataSource.deleteTraining(
            planId = RealmUUID.from(planId),
            dayId = RealmUUID.from(trainingId)
        )
    }

    override suspend fun addExercise(
        planId: String,
        trainingId: String,
        name: String,
        sets: Sets,
        reps: Reps
    ): PlannedExercise {
        val plannedExerciseDbModel = localDataSource.addExercise(
            planId = RealmUUID.from(planId),
            dayId = RealmUUID.from(trainingId),
            name = name,
            sets = sets,
            reps = reps
        )
        return plannedExerciseDbModel.toDomainModel()
    }

    override suspend fun updateExercise(
        planId: String,
        exerciseId: String,
        newName: String,
        newSets: Sets,
        newReps: Reps
    ) {
        localDataSource.updateExercise(
            planId = RealmUUID.from(planId),
            exerciseId = RealmUUID.from(exerciseId),
            newName = newName,
            newSets = newSets,
            newReps = newReps
        )
    }

    override suspend fun deleteExercise(planId: String, exerciseId: String) {
        localDataSource.deleteExercise(
            planId = RealmUUID.from(planId),
            exerciseId = RealmUUID.from(exerciseId)
        )
    }

    override suspend fun reorderExercises(
        planId: String,
        trainingId: String,
        exercisesIds: List<String>
    ) {
        localDataSource.reorderExercises(
            planId = RealmUUID.from(planId),
            dayId = RealmUUID.from(trainingId),
            exercisesIds = exercisesIds.map(RealmUUID::from)
        )
    }
}
