package com.maruchin.gymster.data.plans.datasource

import com.maruchin.gymster.core.database.schema.TrainingPlanDayDbModel
import com.maruchin.gymster.core.database.schema.TrainingPlanDbModel
import com.maruchin.gymster.core.database.schema.TrainingPlanExerciseDbModel
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class PlansLocalDataSource(private val realm: Realm) {

    fun observeAllPlans(): Flow<List<TrainingPlanDbModel>> =
        realm.query<TrainingPlanDbModel>().asFlow().map { change ->
            change.list.toList()
        }

    fun observePlan(planId: RealmUUID): Flow<TrainingPlanDbModel?> =
        realm.query<TrainingPlanDbModel>("_id == $0", planId).asFlow().map { change ->
            change.list.firstOrNull()
        }

    suspend fun createPlan(name: String): RealmUUID {
        val newPlan = TrainingPlanDbModel().also {
            it.name = name
        }
        realm.write {
            copyToRealm(newPlan)
        }
        return newPlan.id
    }

    suspend fun changePlanName(planId: RealmUUID, newName: String) {
        realm.write {
            val plan = query<TrainingPlanDbModel>("_id == $0", planId).find().first()
            plan.name = newName
        }
    }

    suspend fun deletePlan(planId: RealmUUID) {
        realm.write {
            val plan = query<TrainingPlanDbModel>("_id == $0", planId).find().first()
            delete(plan)
        }
    }

    suspend fun addDay(planId: RealmUUID, name: String): RealmUUID {
        val newDay = TrainingPlanDayDbModel().also {
            it.name = name
        }
        realm.write {
            val plan = query<TrainingPlanDbModel>("_id == $0", planId).find().first()
            plan.days.add(newDay)
        }
        return newDay.id
    }

    suspend fun changeDayName(planId: RealmUUID, dayId: RealmUUID, name: String) {
        realm.write {
            val plan = query<TrainingPlanDbModel>("_id == $0", planId).find().first()
            val day = plan.days.find { it.id == dayId }
            day?.name = name
        }
    }

    suspend fun deleteDay(planId: RealmUUID, dayId: RealmUUID) {
        realm.write {
            val trainingPlan = query<TrainingPlanDbModel>("_id == $0", planId).find().first()
            val day = trainingPlan.days.find { it.id == dayId }
            trainingPlan.days.remove(day)
        }
    }

    suspend fun addExercise(
        planId: RealmUUID,
        dayId: RealmUUID,
        name: String,
        sets: Sets,
        reps: Reps
    ): RealmUUID {
        val newExercise = TrainingPlanExerciseDbModel().also {
            it.name = name
            it.regularSets = sets.regular
            it.dropSets = sets.drop
            it.minReps = reps.min
            it.maxReps = reps.max
        }
        realm.write {
            val plan = query<TrainingPlanDbModel>("_id == $0", planId).find().first()
            val day = plan.days.find { it.id == dayId }
            day?.exercises?.add(newExercise)
        }
        return newExercise.id
    }

    suspend fun changeExerciseName(
        planId: RealmUUID,
        dayId: RealmUUID,
        exerciseId: RealmUUID,
        newName: String,
        newSets: Sets,
        newReps: Reps
    ) {
        realm.write {
            val plan = query<TrainingPlanDbModel>("_id == $0", planId).find().first()
            val day = plan.days.find { it.id == dayId }
            val exercise = day?.exercises?.find { it.id == exerciseId }
            exercise?.name = newName
            exercise?.regularSets = newSets.regular
            exercise?.dropSets = newSets.drop
            exercise?.minReps = newReps.min
            exercise?.maxReps = newReps.max
        }
    }

    suspend fun reorderExercises(
        planId: RealmUUID,
        dayId: RealmUUID,
        exercisesIds: List<RealmUUID>
    ) {
        val plan = realm.query<TrainingPlanDbModel>("_id == $0", planId).find().first()
        val day = plan.days.find { it.id == dayId } ?: return
        val reorderedExercises = realmListOf<TrainingPlanExerciseDbModel>()
        for (id in exercisesIds) {
            val exercise = day.exercises.find { it.id == id } ?: continue
            reorderedExercises.add(exercise)
        }
        realm.write {
            findLatest(day)?.let {
                it.exercises = reorderedExercises
            }
        }
    }

    suspend fun deleteExercise(planId: RealmUUID, dayId: RealmUUID, exerciseId: RealmUUID) {
        realm.write {
            val plan = query<TrainingPlanDbModel>("_id == $0", planId).find().first()
            val day = plan.days.find { it.id == dayId }
            val exercise = day?.exercises?.find { it.id == exerciseId }
            day?.exercises?.remove(exercise)
        }
    }
}
