package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.PlannedTraining
import kotlinx.datetime.LocalDate

data class Training(
    val id: String,
    val name: String,
    val planName: String,
    val date: LocalDate,
    val exercises: List<Exercise>
) {

    internal constructor(date: LocalDate, planName: String, plannedTraining: PlannedTraining) : this(
        id = "",
        name = plannedTraining.name,
        planName = planName,
        date = date,
        exercises = plannedTraining.exercises.map { Exercise(it) }
    )

    fun getExercise(exerciseId: String): Exercise = exercises.first { it.id == exerciseId }
}
