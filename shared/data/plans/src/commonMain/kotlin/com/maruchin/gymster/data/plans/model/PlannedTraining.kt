package com.maruchin.gymster.data.plans.model

import com.maruchin.gymster.core.utils.swap

data class PlannedTraining(val id: String, val name: String, val exercises: List<PlannedExercise>) {

    internal fun hasExercise(exerciseId: String) = exercises.any { it.id == exerciseId }

    internal fun changeExercisesOrder(fromId: String, toId: String): PlannedTraining {
        val fromIndex = exercises.indexOfFirst { it.id == fromId }
        val toIndex = exercises.indexOfFirst { it.id == toId }
        return copy(exercises = exercises.swap(fromIndex, toIndex))
    }
}
