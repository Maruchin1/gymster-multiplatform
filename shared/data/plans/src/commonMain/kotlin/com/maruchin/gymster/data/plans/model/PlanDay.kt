package com.maruchin.gymster.data.plans.model

data class PlanDay(val id: String, val name: String, val exercises: List<PlanExercise>)

fun PlanDay.changeExercisesOrder(fromId: String, toId: String): PlanDay {
    val fromIndex = exercises.indexOfFirst { it.id == fromId }
    val toIndex = exercises.indexOfFirst { it.id == toId }
    val newExercises = exercises.toMutableList()
    newExercises.add(toIndex, newExercises.removeAt(fromIndex))
    return copy(exercises = newExercises.toList())
}
