package com.maruchin.gymster.data.plans.model

data class PlannedTraining(val id: String, val name: String, val exercises: List<PlannedExercise>) {

    fun changeExercisesOrder(fromId: String, toId: String): PlannedTraining {
        val fromIndex = exercises.indexOfFirst { it.id == fromId }
        val toIndex = exercises.indexOfFirst { it.id == toId }
        val newExercises = exercises.toMutableList()
        newExercises.add(toIndex, newExercises.removeAt(fromIndex))
        return copy(exercises = newExercises.toList())
    }
}
