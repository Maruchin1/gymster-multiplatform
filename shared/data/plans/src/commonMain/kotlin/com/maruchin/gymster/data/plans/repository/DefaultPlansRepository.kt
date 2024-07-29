package com.maruchin.gymster.data.plans.repository

import com.maruchin.gymster.core.database.schema.TrainingPlanDbModel
import com.maruchin.gymster.data.plans.datasource.PlansLocalDataSource
import com.maruchin.gymster.data.plans.mapper.toDomain
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DefaultPlansRepository(private val localDataSource: PlansLocalDataSource) :
    PlansRepository {

    override fun observeAllPlans(): Flow<List<Plan>> = localDataSource.observeAllPlans().map {
        it.map(TrainingPlanDbModel::toDomain)
    }

    override fun observePlan(planId: String): Flow<Plan?> =
        localDataSource.observePlan(planId = RealmUUID.from(planId)).map {
            it?.toDomain()
        }

    override suspend fun createPlan(name: String): String {
        val id = localDataSource.createPlan(name = name)
        return id.toString()
    }

    override suspend fun changePlanName(planId: String, newName: String) {
        localDataSource.changePlanName(planId = RealmUUID.from(planId), newName = newName)
    }

    override suspend fun deletePlan(planId: String) {
        localDataSource.deletePlan(planId = RealmUUID.from(planId))
    }

    override suspend fun addDay(planId: String, name: String): String {
        val id = localDataSource.addDay(
            planId = RealmUUID.from(planId),
            name = name
        )
        return id.toString()
    }

    override suspend fun changeDayName(planId: String, dayId: String, newName: String) {
        localDataSource.changeDayName(
            planId = RealmUUID.from(planId),
            dayId = RealmUUID.from(dayId),
            name = newName
        )
    }

    override suspend fun deleteDay(planId: String, dayId: String) {
        localDataSource.deleteDay(
            planId = RealmUUID.from(planId),
            dayId = RealmUUID.from(dayId)
        )
    }

    override suspend fun addExercise(
        planId: String,
        dayId: String,
        name: String,
        sets: Sets,
        reps: Reps
    ): String {
        val id = localDataSource.addExercise(
            planId = RealmUUID.from(planId),
            dayId = RealmUUID.from(dayId),
            name = name,
            sets = sets,
            reps = reps
        )
        return id.toString()
    }

    override suspend fun updateExercise(
        planId: String,
        dayId: String,
        exerciseId: String,
        newName: String,
        newSets: Sets,
        newReps: Reps
    ) {
        localDataSource.changeExerciseName(
            planId = RealmUUID.from(planId),
            dayId = RealmUUID.from(dayId),
            exerciseId = RealmUUID.from(exerciseId),
            newName = newName,
            newSets = newSets,
            newReps = newReps
        )
    }

    override suspend fun deleteExercise(planId: String, dayId: String, exerciseId: String) {
        localDataSource.deleteExercise(
            planId = RealmUUID.from(planId),
            dayId = RealmUUID.from(dayId),
            exerciseId = RealmUUID.from(exerciseId)
        )
    }

    override suspend fun reorderExercises(
        planId: String,
        dayId: String,
        exercisesIds: List<String>
    ) {
        localDataSource.reorderExercises(
            planId = RealmUUID.from(planId),
            dayId = RealmUUID.from(dayId),
            exercisesIds = exercisesIds.map(RealmUUID::from)
        )
    }
}
