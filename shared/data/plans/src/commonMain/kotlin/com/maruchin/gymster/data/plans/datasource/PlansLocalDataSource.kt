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

    suspend fun changePlanName(planId: RealmUUID, newName: String) {
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

    suspend fun addWeek(planId: RealmUUID): Map.Entry<Int, List<PlannedTrainingDbModel>> {
        realm.write {
            val plan = query<PlanDbModel>("_id == $0", planId).find().first()
            val weeks = plan.trainings.sortedBy { it.weekIndex }.groupBy { it.weekIndex }.values
            val lastWeek = weeks.last()
            lastWeek.forEach { training ->
                PlannedTrainingDbModel().apply {
                    name = training.name
                    weekIndex = training.weekIndex + 1
                    training.exercises.forEach { exercise ->
                        PlannedExerciseDbModel().apply {
                            name = exercise.name
                            regularSets = exercise.regularSets
                            dropSets = exercise.dropSets
                            minReps = exercise.minReps
                            maxReps = exercise.maxReps
                        }.also {
                            exercises.add(it)
                        }
                    }
                }.also {
                    plan.trainings.add(it)
                }
            }
        }
        return realm.query<PlanDbModel>("_id == $0", planId).find().first().trainings
            .groupBy { it.weekIndex }
            .entries
            .last()
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

    suspend fun changeDayName(planId: RealmUUID, dayId: RealmUUID, name: String) {
        realm.write {
            val plan = query<PlanDbModel>("_id == $0", planId).find().first()
            val day = plan.trainings.find { it.id == dayId }
            day?.name = name
        }
    }

    suspend fun deleteDay(planId: RealmUUID, dayId: RealmUUID) {
        realm.write {
            val trainingPlan = query<PlanDbModel>("_id == $0", planId).find().first()
            val day = trainingPlan.trainings.find { it.id == dayId }
            trainingPlan.trainings.remove(day)
        }
    }

    suspend fun addExercise(
        planId: RealmUUID,
        dayId: RealmUUID,
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
            val day = plan.trainings.find { it.id == dayId }
            day?.exercises?.add(newExercise)
        }
        return newExercise
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
            val plan = query<PlanDbModel>("_id == $0", planId).find().first()
            val day = plan.trainings.find { it.id == dayId }
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
        val plan = realm.query<PlanDbModel>("_id == $0", planId).find().first()
        val day = plan.trainings.find { it.id == dayId } ?: return
        val reorderedExercises = realmListOf<PlannedExerciseDbModel>()
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
            val plan = query<PlanDbModel>("_id == $0", planId).find().first()
            val day = plan.trainings.find { it.id == dayId }
            val exercise = day?.exercises?.find { it.id == exerciseId }
            day?.exercises?.remove(exercise)
        }
    }
}
