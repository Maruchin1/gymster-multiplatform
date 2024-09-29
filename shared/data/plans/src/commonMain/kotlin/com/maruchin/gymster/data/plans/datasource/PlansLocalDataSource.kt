package com.maruchin.gymster.data.plans.datasource

import com.maruchin.gymster.core.database.schema.PlanDbModel
import com.maruchin.gymster.core.database.schema.PlannedExerciseDbModel
import com.maruchin.gymster.core.database.schema.PlannedTrainingDbModel
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class PlansLocalDataSource(private val realm: Realm) {

    fun observeAllPlans(): Flow<List<PlanDbModel>> =
        realm.query<PlanDbModel>().asFlow().map { change ->
            change.list.toList()
        }

    fun observePlan(planId: RealmUUID): Flow<PlanDbModel?> =
        realm.query<PlanDbModel>("_id == $0", planId).asFlow().map { change ->
            change.list.firstOrNull()
        }

    suspend fun createPlan(name: String): PlanDbModel {
        val newPlan = PlanDbModel().also {
            it.name = name
        }
        return realm.write {
            copyToRealm(newPlan)
        }
    }

    suspend fun updatePlan(planId: RealmUUID, newName: String) {
        realm.write {
            val plan = query<PlanDbModel>("_id == $0", planId).find().first()
            plan.name = newName
        }
    }

    suspend fun deletePlan(planId: RealmUUID) {
        realm.write {
            val plan = query<PlanDbModel>("_id == $0", planId).find().first()
            delete(plan)
        }
    }

    suspend fun addTraining(planId: RealmUUID, name: String): PlannedTrainingDbModel {
        val newDay = PlannedTrainingDbModel().also {
            it.name = name
        }
        realm.write {
            val plan = query<PlanDbModel>("_id == $0", planId).find().first()
            plan.trainings.add(newDay)
        }
        return newDay
    }

    suspend fun updateTraining(planId: RealmUUID, trainingIndex: Int, name: String) {
        realm.write {
            val plan = query<PlanDbModel>("_id == $0", planId).find().first()
            val training = plan.trainings[trainingIndex]
            training.name = name
        }
    }

    suspend fun deleteTraining(planId: RealmUUID, trainingIndex: Int) {
        realm.write {
            val trainingPlan = query<PlanDbModel>("_id == $0", planId).find().first()
            trainingPlan.trainings.removeAt(trainingIndex)
        }
    }

    suspend fun addExercise(
        planId: RealmUUID,
        trainingIndex: Int,
        name: String,
        sets: Sets,
        reps: Reps
    ): PlannedExerciseDbModel {
        val newExercise = PlannedExerciseDbModel().also {
            it.name = name
            it.regularSets = sets.regular
            it.dropSets = sets.drop
            it.minReps = reps.min
            it.maxReps = reps.max
        }
        realm.write {
            val plan = query<PlanDbModel>("_id == $0", planId).find().first()
            val training = plan.trainings[trainingIndex]
            training.exercises.add(newExercise)
        }
        return newExercise
    }

    suspend fun updateExercise(
        planId: RealmUUID,
        trainingIndex: Int,
        exerciseIndex: Int,
        newName: String,
        newSets: Sets,
        newReps: Reps
    ) {
        realm.write {
            val plan = query<PlanDbModel>("_id == $0", planId).find().first()
            val exercise = plan.trainings[trainingIndex].exercises[exerciseIndex]
            exercise.name = newName
            exercise.regularSets = newSets.regular
            exercise.dropSets = newSets.drop
            exercise.minReps = newReps.min
            exercise.maxReps = newReps.max
        }
    }

    suspend fun reorderExercises(
        planId: RealmUUID,
        trainingIndex: Int,
        exercisesIds: List<RealmUUID>
    ) {
        val plan = realm.query<PlanDbModel>("_id == $0", planId).find().first()
        val training = plan.trainings[trainingIndex]
        val reorderedExercises = realmListOf<PlannedExerciseDbModel>()
        for (id in exercisesIds) {
            val exercise = training.exercises.find { it.id == id } ?: continue
            reorderedExercises.add(exercise)
        }
        realm.write {
            findLatest(training)?.let {
                it.exercises = reorderedExercises
            }
        }
    }

    suspend fun deleteExercise(planId: RealmUUID, trainingIndex: Int, exerciseIndex: Int) {
        realm.write {
            val plan = query<PlanDbModel>("_id == $0", planId).find().first()
            val training = plan.trainings[trainingIndex]
            training.exercises.removeAt(exerciseIndex)
        }
    }
}
