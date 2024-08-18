package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.PlannedExercise
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets

data class Exercise(
    val id: String,
    val name: String,
    val sets: Sets,
    val reps: Reps,
    val setProgress: List<SetProgress>
) {

    init {
        require(setProgress.size == sets.total) {
            "Progress list size must be equal to total sets"
        }
    }

    val isComplete: Boolean
        get() = setProgress.all { it.isComplete }

    companion object {

        internal fun from(plannedExercise: PlannedExercise) = Exercise(
            id = "",
            name = plannedExercise.name,
            sets = plannedExercise.sets,
            reps = plannedExercise.reps,
            setProgress = List(plannedExercise.sets.regular) {
                SetProgress(id = "", type = SetProgress.Type.REGULAR, progress = null)
            } + List(plannedExercise.sets.drop) {
                SetProgress(id = "", type = SetProgress.Type.DROP, progress = null)
            }
        )
    }
}
