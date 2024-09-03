package com.maruchin.gymster.data.plans.model

import com.maruchin.gymster.core.utils.updated

data class Plan(val id: String, val name: String, val weeks: List<PlannedWeek>) {

    fun getTraining(trainingId: String) =
        weeks.flatMap { it.trainings }.first { it.id == trainingId }

    fun getExercise(exerciseId: String) =
        weeks.flatMap { it.trainings }.flatMap { it.exercises }.first { it.id == exerciseId }

    fun changeExercisesOrder(fromId: String, toId: String) = copy(
        weeks = weeks.updated({ it.hasExercise(fromId) }) { week ->
            week.copy(
                trainings = week.trainings.updated({ it.hasExercise(fromId) }) { training ->
                    training.changeExercisesOrder(fromId, toId)
                }
            )
        }
    )
}
