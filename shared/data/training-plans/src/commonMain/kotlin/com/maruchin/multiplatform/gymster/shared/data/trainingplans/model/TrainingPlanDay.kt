package com.maruchin.multiplatform.gymster.shared.data.trainingplans.model

data class TrainingPlanDay(
    val id: String,
    val name: String,
    val exercises: List<TrainingPlanExercise>
)

fun TrainingPlanDay.changeExercisesOrder(fromId: String, toId: String): TrainingPlanDay {
    val fromIndex = exercises.indexOfFirst { it.id == fromId }
    val toIndex = exercises.indexOfFirst { it.id == toId }
    val newExercises = exercises.toMutableList()
    newExercises.add(toIndex, newExercises.removeAt(fromIndex))
    return copy(exercises = newExercises.toList())
}
