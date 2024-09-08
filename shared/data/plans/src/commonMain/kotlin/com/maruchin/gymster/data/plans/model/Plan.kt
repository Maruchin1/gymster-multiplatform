package com.maruchin.gymster.data.plans.model

import com.maruchin.gymster.core.utils.updated

data class Plan(val id: String, val name: String, val trainings: List<PlannedTraining>) {

    fun getExercise(exerciseId: String) =
        trainings.flatMap { it.exercises }.first { it.id == exerciseId }

    fun changeExerciseOrder(fromId: String, toId: String) = copy(
        trainings = trainings.updated({ it.hasExercise(fromId) }) { training ->
            training.changeExercisesOrder(fromId, toId)
        }
    )
}
