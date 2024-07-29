package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.PlanDay
import kotlinx.datetime.LocalDate

data class Training(val id: String, val date: LocalDate, val exercises: List<TrainingExercise>) {

    internal constructor(date: LocalDate, planDay: PlanDay) : this(
        id = "",
        date = date,
        exercises = planDay.exercises.map { TrainingExercise(it) }
    )
}
