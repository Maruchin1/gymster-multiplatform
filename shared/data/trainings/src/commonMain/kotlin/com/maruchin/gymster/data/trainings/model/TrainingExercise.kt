package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.PlanExercise
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets

data class TrainingExercise(
    val id: String,
    val name: String,
    val sets: Sets,
    val reps: Reps,
    val progress: List<Progress>
) {

    internal constructor(planExercise: PlanExercise) : this(
        id = "",
        name = planExercise.name,
        sets = planExercise.sets,
        reps = planExercise.reps,
        progress = List(planExercise.sets.total) { Progress() }
    )

    init {
        require(progress.size == sets.total) {
            "Progress list size must be equal to total sets"
        }
    }

    fun getProgress(index: Int): Progress = progress[index]
}
