package com.maruchin.multiplatform.gymster.shared.data.trainingplans.model

data class TrainingPlan(val id: String, val name: String, val days: List<TrainingPlanDay>)

fun TrainingPlan.findDay(dayId: String) = days.find { it.id == dayId }

fun TrainingPlan.findExercise(exerciseId: String) =
    days.flatMap { it.exercises }.find { it.id == exerciseId }

fun TrainingPlan.changeExercisesOrder(fromId: String, toId: String) = copy(
    days = days.map { day ->
        if (day.exercises.find { it.id == fromId } != null) {
            day.changeExercisesOrder(fromId, toId)
        } else {
            day
        }
    }
)
