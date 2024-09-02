package com.maruchin.gymster.data.plans.repository

import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.PlannedExercise
import com.maruchin.gymster.data.plans.model.PlannedTraining
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakePlansRepository : PlansRepository {

    private val collection = MutableStateFlow<Map<String, Plan>>(emptyMap())

    fun setPlans(plans: List<Plan>) {
        collection.value = plans.associateBy { it.id }
    }

    override fun observeAllPlans(): Flow<List<Plan>> = collection.map {
        it.values.toList()
    }

    override fun observePlan(planId: String): Flow<Plan?> = collection.map { it[planId] }

    override suspend fun createPlan(name: String): Plan {
        val plans = collection.value
        val id = (plans.size + 1).toString()
        val newPlan = Plan(
            id = id,
            name = name,
            weeksDuration = Plan.DEFAULT_WEEKS_DURATION,
            trainings = emptyList()
        )
        collection.value += id to newPlan
        return newPlan
    }

    override suspend fun changePlanName(planId: String, newName: String) {
        collection.value[planId]?.let { trainingPlan ->
            collection.value += planId to trainingPlan.copy(name = newName)
        }
    }

    override suspend fun changePlanDuration(planId: String, newDuration: Int) {
        collection.value[planId]?.let { trainingPlan ->
            collection.value += planId to trainingPlan.copy(weeksDuration = newDuration)
        }
    }

    override suspend fun deletePlan(planId: String) {
        collection.value -= planId
    }

    override suspend fun addTraining(
        planId: String,
        weekIndex: Int,
        name: String
    ): PlannedTraining {
        val days = collection.value[planId]?.trainings ?: emptyList()
        val id = (days.size + 1).toString()
        val newTraining = PlannedTraining(
            id = id,
            name = name,
            weekIndex = weekIndex,
            exercises = emptyList()
        )
        collection.value +=
            planId to collection.value[planId]!!.copy(trainings = days + newTraining)
        return newTraining
    }

    override suspend fun changeTrainingName(planId: String, trainingId: String, newName: String) {
        collection.value[planId]?.let { plan ->
            val newDays = plan.trainings.map { day ->
                if (day.id == trainingId) {
                    day.copy(name = newName)
                } else {
                    day
                }
            }
            collection.value += planId to plan.copy(trainings = newDays)
        }
    }

    override suspend fun deleteTraining(planId: String, trainingId: String) {
        collection.value[planId]?.let { plan ->
            val newDays = plan.trainings.filter { it.id != trainingId }
            collection.value += planId to plan.copy(trainings = newDays)
        }
    }

    override suspend fun addExercise(
        planId: String,
        trainingId: String,
        name: String,
        sets: Sets,
        reps: Reps
    ): PlannedExercise {
        val plans = collection.value
        val days = plans[planId]?.trainings ?: emptyList()
        val exercises = days.find { it.id == trainingId }?.exercises ?: emptyList()
        val id = (exercises.size + 1).toString()
        val newExercise = PlannedExercise(
            id = id,
            name = name,
            sets = sets,
            reps = reps
        )
        val newDays = days.map { day ->
            if (day.id == trainingId) {
                day.copy(exercises = exercises + newExercise)
            } else {
                day
            }
        }
        collection.value += planId to plans[planId]!!.copy(trainings = newDays)
        return newExercise
    }

    override suspend fun updateExercise(
        planId: String,
        trainingId: String,
        exerciseId: String,
        newName: String,
        newSets: Sets,
        newReps: Reps
    ) {
        collection.value[planId]?.let { plan ->
            val newDays = plan.trainings.map { day ->
                if (day.id == trainingId) {
                    val newExercises = day.exercises.map { exercise ->
                        if (exercise.id == exerciseId) {
                            exercise.copy(name = newName, sets = newSets, reps = newReps)
                        } else {
                            exercise
                        }
                    }
                    day.copy(exercises = newExercises)
                } else {
                    day
                }
            }
            collection.value += planId to plan.copy(trainings = newDays)
        }
    }

    override suspend fun deleteExercise(planId: String, trainingId: String, exerciseId: String) {
        collection.value[planId]?.let { plan ->
            val newDays = plan.trainings.map { day ->
                if (day.id == trainingId) {
                    val newExercises = day.exercises.filter { it.id != exerciseId }
                    day.copy(exercises = newExercises)
                } else {
                    day
                }
            }
            collection.value += planId to plan.copy(trainings = newDays)
        }
    }

    override suspend fun reorderExercises(
        planId: String,
        trainingId: String,
        exercisesIds: List<String>
    ) {
        collection.value[planId]?.let { plan ->
            val newDays = plan.trainings.map { day ->
                if (day.id == trainingId) {
                    val newExercises = day.exercises.sortedBy { exercisesIds.indexOf(it.id) }
                    day.copy(exercises = newExercises)
                } else {
                    day
                }
            }
            collection.value += planId to plan.copy(trainings = newDays)
        }
    }
}
