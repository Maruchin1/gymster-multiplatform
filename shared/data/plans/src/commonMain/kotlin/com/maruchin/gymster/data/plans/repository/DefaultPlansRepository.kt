package com.maruchin.gymster.data.plans.repository

import com.maruchin.gymster.core.database.schema.PlanDbModel
import com.maruchin.gymster.data.plans.datasource.PlansLocalDataSource
import com.maruchin.gymster.data.plans.mapper.toDomain
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DefaultPlansRepository(
    private val localDataSource: PlansLocalDataSource,
    private val scope: CoroutineScope
) : PlansRepository {

    override fun observeAllPlans(): Flow<List<Plan>> = localDataSource.observeAllPlans().map {
        it.map(PlanDbModel::toDomain)
    }

    override fun observePlan(planId: String): Flow<Plan?> =
        localDataSource.observePlan(planId = RealmUUID.from(planId)).map {
            it?.toDomain()
        }

    override suspend fun createPlan(name: String): String = scope.async {
        val id = localDataSource.createPlan(
            name = name,
            weeksDuration = Plan.DEFAULT_WEEKS_DURATION
        )
        id.toString()
    }.await()

    override suspend fun changePlanName(planId: String, newName: String) = scope.async {
        localDataSource.changePlanName(planId = RealmUUID.from(planId), newName = newName)
    }.await()

    override suspend fun changePlanDuration(planId: String, newDuration: Int) = scope.async {
        localDataSource.changePlanDuration(
            planId = RealmUUID.from(planId),
            newDuration = newDuration
        )
    }.await()

    override suspend fun deletePlan(planId: String) = scope.async {
        localDataSource.deletePlan(planId = RealmUUID.from(planId))
    }.await()

    override suspend fun addTraining(planId: String, name: String): String = scope.async {
        val id = localDataSource.addDay(
            planId = RealmUUID.from(planId),
            name = name
        )
        id.toString()
    }.await()

    override suspend fun changeTrainingName(planId: String, trainingId: String, newName: String) =
        scope.async {
            localDataSource.changeDayName(
                planId = RealmUUID.from(planId),
                dayId = RealmUUID.from(trainingId),
                name = newName
            )
        }.await()

    override suspend fun deleteTraining(planId: String, trainingId: String) = scope.async {
        localDataSource.deleteDay(
            planId = RealmUUID.from(planId),
            dayId = RealmUUID.from(trainingId)
        )
    }.await()

    override suspend fun addExercise(
        planId: String,
        trainingId: String,
        name: String,
        sets: Sets,
        reps: Reps
    ): String = scope.async {
        val id = localDataSource.addExercise(
            planId = RealmUUID.from(planId),
            dayId = RealmUUID.from(trainingId),
            name = name,
            sets = sets,
            reps = reps
        )
        id.toString()
    }.await()

    override suspend fun updateExercise(
        planId: String,
        trainingId: String,
        exerciseId: String,
        newName: String,
        newSets: Sets,
        newReps: Reps
    ) = scope.async {
        localDataSource.changeExerciseName(
            planId = RealmUUID.from(planId),
            dayId = RealmUUID.from(trainingId),
            exerciseId = RealmUUID.from(exerciseId),
            newName = newName,
            newSets = newSets,
            newReps = newReps
        )
    }.await()

    override suspend fun deleteExercise(planId: String, trainingId: String, exerciseId: String) =
        scope.async {
            localDataSource.deleteExercise(
                planId = RealmUUID.from(planId),
                dayId = RealmUUID.from(trainingId),
                exerciseId = RealmUUID.from(exerciseId)
            )
        }.await()

    override suspend fun reorderExercises(
        planId: String,
        trainingId: String,
        exercisesIds: List<String>
    ) = scope.async {
        localDataSource.reorderExercises(
            planId = RealmUUID.from(planId),
            dayId = RealmUUID.from(trainingId),
            exercisesIds = exercisesIds.map(RealmUUID::from)
        )
    }.await()
}
