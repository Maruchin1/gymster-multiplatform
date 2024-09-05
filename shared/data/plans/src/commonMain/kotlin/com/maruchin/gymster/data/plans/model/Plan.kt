package com.maruchin.gymster.data.plans.model

data class Plan(val id: String, val name: String, val weeks: List<PlannedWeek>) {

    fun getTraining(trainingId: String) =
        weeks.flatMap { it.trainings }.first { it.id == trainingId }

    fun getExercise(exerciseId: String) =
        weeks.flatMap { it.trainings }.flatMap { it.exercises }.first { it.id == exerciseId }
}
