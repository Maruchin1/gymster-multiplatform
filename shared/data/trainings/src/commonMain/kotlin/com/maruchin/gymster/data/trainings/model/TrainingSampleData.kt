package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import kotlinx.datetime.LocalDate

val sampleTrainings = listOf(
    Training(
        id = "1",
        date = LocalDate(2024, 7, 24),
        exercises = listOf(
            TrainingExercise(
                id = "1",
                name = "Bench Press",
                sets = Sets(regular = 3),
                reps = Reps(4..6),
                progress = listOf(
                    Progress(weight = 70.0, reps = 6),
                    Progress(weight = 70.0, reps = 5),
                    Progress(weight = 70.0, reps = 4)
                )
            ),
            TrainingExercise(
                id = "2",
                name = "Overhead Press",
                sets = Sets(regular = 3),
                reps = Reps(10..12),
                progress = listOf(
                    Progress(weight = 40.0, reps = 12),
                    Progress(weight = 40.0, reps = 12),
                    Progress(weight = 40.0, reps = 12)
                )
            ),
            TrainingExercise(
                id = "3",
                name = "Triceps Extension",
                sets = Sets(regular = 1, drop = 2),
                reps = Reps(10..20),
                progress = listOf(
                    Progress(weight = 20.0, reps = 20),
                    Progress(weight = 20.0, reps = 15),
                    Progress(weight = 20.0, reps = 10)
                )
            )
        )
    )
)
