package com.maruchin.gymster.data.trainings.model

import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.TrainingPlanDay
import kotlinx.datetime.LocalDate

data class Training(val id: String, val date: LocalDate, val exercises: List<TrainingExercise>) {

    internal constructor(date: LocalDate, planDay: TrainingPlanDay) : this(
        id = "",
        date = date,
        exercises = planDay.exercises.map { TrainingExercise(it) }
    )
}
