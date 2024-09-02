package com.maruchin.gymster.data.plans.model

import com.maruchin.gymster.core.utils.updated

data class Plan(val id: String, val name: String, val trainings: List<PlannedTraining>) {

    val weeks: List<List<PlannedTraining>>
        get() = trainings.groupBy { it.weekIndex }
            .entries
            .sortedBy { (week, _) -> week }
            .map { (_, trainings) -> trainings }

    val weeksDuration: Int
        get() = trainings.map { it.weekIndex }.distinct().size

    fun getTraining(trainingId: String) = trainings.first { it.id == trainingId }

    fun getExercise(exerciseId: String) =
        trainings.flatMap { it.exercises }.first { it.id == exerciseId }

    fun changeExercisesOrder(fromId: String, toId: String) = copy(
        trainings = trainings.updated({ it.hasExercise(fromId) }) { plannedTraining ->
            plannedTraining.changeExercisesOrder(fromId, toId)
        }
    )
}
