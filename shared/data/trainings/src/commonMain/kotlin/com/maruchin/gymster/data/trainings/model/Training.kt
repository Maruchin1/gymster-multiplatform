package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.PlannedTraining

data class Training(val id: String, val name: String, val exercises: List<Exercise>) {

    fun getExercise(exerciseId: String): Exercise = exercises.first { it.id == exerciseId }

    companion object {

        internal fun from(plannedTraining: PlannedTraining) = Training(
            id = "",
            name = plannedTraining.name,
            exercises = plannedTraining.exercises.map { Exercise.from(it) }
        )
    }
}
