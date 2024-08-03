package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.PlanDay
import kotlinx.datetime.LocalDate

data class Training(
    val id: String,
    val name: String,
    val planName: String,
    val date: LocalDate,
    val exercises: List<TrainingExercise>
) {

    internal constructor(date: LocalDate, planName: String, planDay: PlanDay) : this(
        id = "",
        name = planDay.name,
        planName = planName,
        date = date,
        exercises = planDay.exercises.map { TrainingExercise(it) }
    )

    fun getExercise(exerciseId: String): TrainingExercise = exercises.first { it.id == exerciseId }
}
