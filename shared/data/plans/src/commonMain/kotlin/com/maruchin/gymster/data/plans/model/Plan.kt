package com.maruchin.gymster.data.plans.model

data class Plan(val id: String, val name: String, val days: List<PlanDay>) {

    fun findDay(dayId: String) = days.find { it.id == dayId }

    fun findExercise(exerciseId: String) =
        days.flatMap { it.exercises }.find { it.id == exerciseId }

    fun changeExercisesOrder(fromId: String, toId: String) = copy(
        days = days.map { day ->
            if (day.exercises.find { it.id == fromId } != null) {
                day.changeExercisesOrder(fromId, toId)
            } else {
                day
            }
        }
    )
}
