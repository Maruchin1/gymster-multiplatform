package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.PlannedExercise
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets

data class Exercise(
    val id: String,
    val name: String,
    val sets: Sets,
    val reps: Reps,
    val progress: List<Progress>
) {

    internal constructor(plannedExercise: PlannedExercise) : this(
        id = "",
        name = plannedExercise.name,
        sets = plannedExercise.sets,
        reps = plannedExercise.reps,
        progress = List(plannedExercise.sets.total) { Progress() }
    )

    init {
        require(progress.size == sets.total) {
            "Progress list size must be equal to total sets"
        }
    }

    val isComplete: Boolean
        get() = progress.all { it.isComplete }

    fun getProgress(index: Int): Progress = progress[index]
}
