package com.maruchin.multiplatform.gymster.data.trainingplans.repository

import com.maruchin.multiplatform.gymster.data.trainingplans.model.Reps
import com.maruchin.multiplatform.gymster.data.trainingplans.model.Sets
import com.maruchin.multiplatform.gymster.data.trainingplans.model.TrainingPlan
import com.maruchin.multiplatform.gymster.data.trainingplans.model.TrainingPlanDay
import com.maruchin.multiplatform.gymster.data.trainingplans.model.TrainingPlanExercise
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

internal class FakeTrainingPlansRepository : TrainingPlansRepository {

    private val collection = MutableStateFlow<Map<String, TrainingPlan>>(emptyMap())

    fun getPlans(): List<TrainingPlan> = collection.value.values.toList()

    fun setAvailablePlans() {
        collection.value = listOf(
            TrainingPlan(
                id = "1",
                name = "Push Pull",
                days = listOf(
                    TrainingPlanDay(
                        id = "1",
                        name = "Push 1",
                        exercises = listOf(
                            TrainingPlanExercise(
                                id = "1",
                                name = "Bench Press",
                                sets = Sets(regular = 3),
                                reps = Reps(4..6)
                            ),
                            TrainingPlanExercise(
                                id = "2",
                                name = "Overhead Press",
                                sets = Sets(regular = 3),
                                reps = Reps(10..12)
                            ),
                            TrainingPlanExercise(
                                id = "3",
                                name = "Triceps Extension",
                                sets = Sets(regular = 1, drop = 2),
                                reps = Reps(10..20)
                            )
                        )
                    ),
                    TrainingPlanDay(
                        id = "2",
                        name = "Pull 1",
                        exercises = emptyList()
                    ),
                    TrainingPlanDay(
                        id = "3",
                        name = "Push 2",
                        exercises = emptyList()
                    ),
                    TrainingPlanDay(
                        id = "4",
                        name = "Pull 2",
                        exercises = emptyList()
                    )
                )
            ),
            TrainingPlan(id = "2", name = "Full Body Workout", days = emptyList())
        ).associateBy { it.id }
    }

    fun setNoPlans() {
        collection.value = emptyMap()
    }

    override fun observeAllPlans(): Flow<List<TrainingPlan>> = collection.map {
        it.values.toList()
    }

    override fun observePlan(planId: String): Flow<TrainingPlan?> = collection.map { it[planId] }

    override suspend fun createPlan(name: String): String {
        val plans = collection.value
        val id = (plans.size + 1).toString()
        val newPlan = TrainingPlan(
            id = id,
            name = name,
            days = emptyList()
        )
        collection.value += id to newPlan
        return id
    }

    override suspend fun changePlanName(planId: String, newName: String) {
        collection.value[planId]?.let { trainingPlan ->
            collection.value += planId to trainingPlan.copy(name = newName)
        }
    }

    override suspend fun deletePlan(planId: String) {
        collection.value -= planId
    }

    override suspend fun addDay(planId: String, name: String): String {
        val plans = collection.value
        val days = collection.value[planId]?.days ?: emptyList()
        val id = (days.size + 1).toString()
        val newDay = TrainingPlanDay(
            id = id,
            name = name,
            exercises = emptyList()
        )
        collection.value += planId to collection.value[planId]!!.copy(days = days + newDay)
        return id
    }

    override suspend fun changeDayName(planId: String, dayId: String, newName: String) {
        collection.value[planId]?.let { plan ->
            val newDays = plan.days.map { day ->
                if (day.id == dayId) {
                    day.copy(name = newName)
                } else {
                    day
                }
            }
            collection.value += planId to plan.copy(days = newDays)
        }
    }

    override suspend fun deleteDay(planId: String, dayId: String) {
        collection.value[planId]?.let { plan ->
            val newDays = plan.days.filter { it.id != dayId }
            collection.value += planId to plan.copy(days = newDays)
        }
    }

    override suspend fun addExercise(
        planId: String,
        dayId: String,
        name: String,
        sets: Sets,
        reps: Reps
    ): String {
        val plans = collection.value
        val days = plans[planId]?.days ?: emptyList()
        val exercises = days.find { it.id == dayId }?.exercises ?: emptyList()
        val id = (exercises.size + 1).toString()
        val newExercise = TrainingPlanExercise(
            id = id,
            name = name,
            sets = sets,
            reps = reps
        )
        val newDays = days.map { day ->
            if (day.id == dayId) {
                day.copy(exercises = exercises + newExercise)
            } else {
                day
            }
        }
        collection.value += planId to plans[planId]!!.copy(days = newDays)
        return id
    }

    override suspend fun updateExercise(
        planId: String,
        dayId: String,
        exerciseId: String,
        newName: String,
        newSets: Sets,
        newReps: Reps
    ) {
        collection.value[planId]?.let { plan ->
            val newDays = plan.days.map { day ->
                if (day.id == dayId) {
                    val newExercises = day.exercises.map { exercise ->
                        if (exercise.id == exerciseId) {
                            exercise.copy(name = newName)
                        } else {
                            exercise
                        }
                    }
                    day.copy(exercises = newExercises)
                } else {
                    day
                }
            }
            collection.value += planId to plan.copy(days = newDays)
        }
    }

    override suspend fun deleteExercise(planId: String, dayId: String, exerciseId: String) {
        collection.value[planId]?.let { plan ->
            val newDays = plan.days.map { day ->
                if (day.id == dayId) {
                    val newExercises = day.exercises.filter { it.id != exerciseId }
                    day.copy(exercises = newExercises)
                } else {
                    day
                }
            }
            collection.value += planId to plan.copy(days = newDays)
        }
    }
}
