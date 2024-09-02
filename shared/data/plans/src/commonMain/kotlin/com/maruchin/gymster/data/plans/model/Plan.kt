package com.maruchin.gymster.data.plans.model

import com.maruchin.gymster.core.utils.updated

data class Plan(
    val id: String,
    val name: String,
    val weeksDuration: Int,
    val trainings: List<PlannedTraining>
) {

    init {
        require(weeksDuration > 0) { "weeksDuration must be greater than 0" }
    }

    val weeks: List<List<PlannedTraining>>
        get() = trainings.groupBy { it.weekIndex }
            .entries
            .sortedBy { (week, _) -> week }
            .map { (_, trainings) -> trainings }

    fun getTraining(trainingId: String) = trainings.first { it.id == trainingId }

    fun getExercise(exerciseId: String) =
        trainings.flatMap { it.exercises }.first { it.id == exerciseId }

    fun changeExercisesOrder(fromId: String, toId: String) = copy(
        trainings = trainings.updated({ it.hasExercise(fromId) }) { plannedTraining ->
            plannedTraining.changeExercisesOrder(fromId, toId)
        }
    )

    companion object {

        const val DEFAULT_WEEKS_DURATION = 8
    }
}
