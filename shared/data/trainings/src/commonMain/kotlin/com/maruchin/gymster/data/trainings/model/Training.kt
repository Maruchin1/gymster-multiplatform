package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.PlannedTraining

data class Training(val id: String, val name: String, val exercises: List<Exercise>) {

    val isComplete: Boolean
        get() = exercises.all { it.isComplete }

    fun getExerciseIndex(exerciseId: String): Int = exercises.indexOfFirst { it.id == exerciseId }

    companion object {

        internal fun from(plannedTraining: PlannedTraining) = Training(
            id = "",
            name = plannedTraining.name,
            exercises = plannedTraining.exercises.map { Exercise.from(it) }
        )
    }
}
