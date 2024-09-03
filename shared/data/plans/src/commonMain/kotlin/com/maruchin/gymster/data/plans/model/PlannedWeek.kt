package com.maruchin.gymster.data.plans.model

data class PlannedWeek(val trainings: List<PlannedTraining>) {

    internal fun hasTraining(trainingId: String): Boolean = trainings.any { it.id == trainingId }

    internal fun hasExercise(exerciseId: String): Boolean =
        trainings.any { it.hasExercise(exerciseId) }
}
