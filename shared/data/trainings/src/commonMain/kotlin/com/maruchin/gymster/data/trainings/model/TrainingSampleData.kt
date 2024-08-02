package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import kotlinx.datetime.LocalDate

val sampleTrainings = listOf(
    Training(
        id = "1",
        name = "Push",
        planName = "Push Pull",
        date = LocalDate(2024, 7, 24),
        exercises = listOf(
            TrainingExercise(
                id = "1",
                name = "Bench Press",
                sets = Sets(regular = 3),
                reps = Reps(4..6),
                progress = listOf(
                    Progress(70.0 to 6),
                    Progress(70.0 to 5),
                    Progress(70.0 to 4)
                )
            ),
            TrainingExercise(
                id = "2",
                name = "Overhead Press",
                sets = Sets(regular = 3),
                reps = Reps(10..12),
                progress = listOf(
                    Progress(40.0 to 12),
                    Progress(40.0 to 12),
                    Progress(40.0 to 12)
                )
            ),
            TrainingExercise(
                id = "3",
                name = "Triceps Extension",
                sets = Sets(regular = 1, drop = 3),
                reps = Reps(10..20),
                progress = listOf(
                    Progress(20.0 to 20),
                    Progress(20.0 to 15),
                    Progress(20.0 to 10),
                    Progress(20.0 to 10)
                )
            ),
            TrainingExercise(
                id = "4",
                name = "Lateral Raises",
                sets = Sets(regular = 3),
                reps = Reps(12..15),
                progress = listOf(Progress(), Progress(), Progress())
            )
        )
    )
)
