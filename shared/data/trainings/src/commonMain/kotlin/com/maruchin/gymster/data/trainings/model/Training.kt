package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.core.utils.uuid
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.PlannedTraining
import kotlinx.datetime.LocalDate

data class Training(
    val id: String,
    val name: String,
    val planName: String,
    val date: LocalDate,
    val isComplete: Boolean,
    val exercises: List<Exercise>
) {

    fun getSetResult(setResultId: String): SetResult? = exercises
        .flatMap { it.results }
        .find { it.id == setResultId }

    fun hasSetResult(setResultId: String): Boolean = exercises.any { it.hasSetResult(setResultId) }

    companion object {

        internal fun from(plan: Plan, plannedTraining: PlannedTraining, date: LocalDate) = Training(
            id = uuid(),
            name = plannedTraining.name,
            planName = plan.name,
            date = date,
            isComplete = false,
            exercises = plannedTraining.exercises.map { Exercise.from(it) }
        )
    }
}
