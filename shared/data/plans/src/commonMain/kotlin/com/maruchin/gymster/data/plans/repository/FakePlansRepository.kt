package com.maruchin.gymster.data.plans.repository

import com.maruchin.gymster.core.utils.updated
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
            trainings = emptyList()
        )
        collection.value += id to newPlan
        return newPlan
    }

    override suspend fun changePlanName(planId: String, newName: String) {
        val plan = collection.value[planId]!!
        val updatedPlan = plan.copy(name = newName)
        collection.value += planId to updatedPlan
    }

    override suspend fun deletePlan(planId: String) {
        collection.value -= planId
    }

    override suspend fun addTraining(planId: String, name: String): PlannedTraining {
        val plan = collection.value[planId]!!
        val allTrainings = plan.trainings
        val id = (allTrainings.size + 1).toString()
        val newTraining = PlannedTraining(
            id = id,
            name = name,
            exercises = emptyList()
        )
        val updatedPlan = plan.copy(
            trainings = plan.trainings + newTraining
        )
        collection.value += planId to updatedPlan
        return newTraining
    }

    override suspend fun changeTrainingName(planId: String, trainingId: String, newName: String) {
        val plan = collection.value[planId]!!
        val updatedPlan = plan.copy(
            trainings = plan.trainings.updated({ it.id == trainingId }) { training ->
                training.copy(name = newName)
            }
        )
        collection.value += planId to updatedPlan
    }

    override suspend fun deleteTraining(planId: String, trainingId: String) {
        val plan = collection.value[planId]!!
        val updatedPlan = plan.copy(
            trainings = plan.trainings.filter { it.id != trainingId }
        )
        collection.value += planId to updatedPlan
    }

    override suspend fun addExercise(
        planId: String,
        trainingId: String,
        name: String,
        sets: Sets,
        reps: Reps
    ): PlannedExercise {
        val plan = collection.value[planId]!!
        val allExercises = plan.trainings.flatMap { it.exercises }
        val newId = (allExercises.size + 1).toString()
        val newExercise = PlannedExercise(
            id = newId,
            name = name,
            sets = sets,
            reps = reps
        )
        val updatedPlan = plan.copy(
            trainings = plan.trainings.updated({ it.id == trainingId }) { training ->
                training.copy(exercises = training.exercises + newExercise)
            }
        )
        collection.value += planId to updatedPlan
        return newExercise
    }

    override suspend fun updateExercise(
        planId: String,
        exerciseId: String,
        newName: String,
        newSets: Sets,
        newReps: Reps
    ) {
        val plan = collection.value[planId]!!
        val updatedPlan = plan.copy(
            trainings = plan.trainings.updated(
                predicate = { training -> training.exercises.any { it.id == exerciseId } }
            ) { training ->
                training.copy(
                    exercises = training.exercises.updated({ it.id == exerciseId }) { exercise ->
                        exercise.copy(name = newName, sets = newSets, reps = newReps)
                    }
                )
            }
        )
        collection.value += planId to updatedPlan
    }

    override suspend fun deleteExercise(planId: String, exerciseId: String) {
        val plan = collection.value[planId]!!
        val updatedPlan = plan.copy(
            trainings = plan.trainings.updated(
                predicate = { training -> training.exercises.any { it.id == exerciseId } }
            ) { training ->
                training.copy(
                    exercises = training.exercises.filter { it.id != exerciseId }
                )
            }
        )
        collection.value += planId to updatedPlan
    }

    override suspend fun reorderExercises(
        planId: String,
        trainingId: String,
        exercisesIds: List<String>
    ) {
        val plan = collection.value[planId]!!
        val updatedPlan = plan.copy(
            trainings = plan.trainings.updated({ it.id == trainingId }) { training ->
                val newExercises = training.exercises.sortedBy {
                    exercisesIds.indexOf(it.id)
                }
                training.copy(exercises = newExercises)
            }
        )
        collection.value += planId to updatedPlan
    }
}
