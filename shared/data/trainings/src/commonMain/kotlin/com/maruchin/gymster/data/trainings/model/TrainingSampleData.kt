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
                name = "Wyciskanie sztangi na ławce poziomej",
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
                name = "Rozpiętki hantlami na ławce skos dodatni",
                sets = Sets(regular = 2, drop = 1),
                reps = Reps(10..12),
                progress = listOf(
                    Progress(17.5 to 12),
                    Progress(17.5 to 12),
                    Progress(17.5 to 12)
                )
            ),
            TrainingExercise(
                id = "3",
                name = "Wyciskanie hantlami nad głowę siedząc",
                sets = Sets(regular = 2, drop = 1),
                reps = Reps(8..10),
                progress = listOf(Progress(), Progress(), Progress())
            ),
            TrainingExercise(
                id = "4",
                name = "Wznosy hantli bokiem stojąc",
                sets = Sets(regular = 1, drop = 3),
                reps = Reps(10..20),
                progress = listOf(Progress(), Progress(), Progress(), Progress())
            ),
            TrainingExercise(
                id = "5",
                name = "Prostowanie ramion na wyciągu",
                sets = Sets(regular = 2, drop = 1),
                reps = Reps(10..12),
                progress = listOf(Progress(), Progress(), Progress())
            )
        )
    )
)
