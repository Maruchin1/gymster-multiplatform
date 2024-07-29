package com.maruchin.gymster.data.trainings.model

import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.Reps
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.Sets
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.TrainingPlanExercise

data class TrainingExercise(
    val id: String,
    val name: String,
    val sets: Sets,
    val reps: Reps,
    val progress: List<Progress>
) {

    internal constructor(planExercise: TrainingPlanExercise) : this(
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
}
