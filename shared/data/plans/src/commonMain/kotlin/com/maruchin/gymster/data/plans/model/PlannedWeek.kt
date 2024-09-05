package com.maruchin.gymster.data.plans.model

import com.maruchin.gymster.core.utils.updated

data class PlannedWeek(val trainings: List<PlannedTraining>) {

    fun hasTraining(trainingId: String): Boolean = trainings.any { it.id == trainingId }

    fun changeExerciseOrder(fromId: String, toId: String) = copy(
        trainings = trainings.updated({ it.hasExercise(fromId) }) { training ->
            training.changeExercisesOrder(fromId, toId)
        }
    )
}
