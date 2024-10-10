package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.PlannedExercise
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import kotlin.uuid.Uuid

data class Exercise(
    val id: String,
    val name: String,
    val sets: Sets,
    val reps: Reps,
    val results: List<SetResult>
) {

    init {
        require(results.size == sets.total) {
            "Results size must be equal to total sets"
        }
    }

    val isComplete: Boolean
        get() = results.all { it.isComplete }

    val evaluation: Evaluation
        get() = when {
            !isComplete -> Evaluation.NONE
            results.any { it.isTooMuch(reps) } -> Evaluation.LESS
            results.any { it.isTooLittle(reps) } -> Evaluation.MORE
            else -> Evaluation.GOOD
        }

    fun hasSetResult(setResultId: String): Boolean = results.any { it.id == setResultId }

    companion object {

        internal fun from(plannedExercise: PlannedExercise) = Exercise(
            id = Uuid.random().toString(),
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
