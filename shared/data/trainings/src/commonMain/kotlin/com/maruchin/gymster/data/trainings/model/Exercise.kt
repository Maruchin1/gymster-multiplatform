package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.PlannedExercise
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets

data class Exercise(
    val id: String,
    val name: String,
    val sets: Sets,
    val reps: Reps,
    val results: List<SetResult>
    // TODO Add score (less, neutral, more)
) {

    init {
        require(results.size == sets.total) {
            "Progress list size must be equal to total sets"
        }
    }

    val isComplete: Boolean
        get() = results.all { it.isComplete }

    companion object {

        internal fun from(plannedExercise: PlannedExercise) = Exercise(
            id = "",
            name = plannedExercise.name,
            sets = plannedExercise.sets,
            reps = plannedExercise.reps,
            results = List(plannedExercise.sets.regular) {
                SetResult.emptyRegular()
            } + List(plannedExercise.sets.drop) {
                SetResult.emptyDrop()
            }
        )
    }
}
