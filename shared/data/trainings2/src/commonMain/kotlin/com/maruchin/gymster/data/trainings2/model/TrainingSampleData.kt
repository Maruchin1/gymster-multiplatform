package com.maruchin.gymster.data.trainings2.model

import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import kotlinx.datetime.LocalDate

val samplePushTraining = Training(
    id = "1",
    name = "Push",
    planName = "Push Pull Legs",
    date = LocalDate(2024, 10, 7),
    isComplete = false,
    exercises = listOf(
        Exercise(
            id = "1",
            name = "Wyciskanie sztangi na ławce poziomej",
            sets = Sets(regular = 3),
            reps = Reps(4..6),
            results = listOf(
                SetResult(id = "1", type = SetResult.Type.REGULAR, weight = null, reps = null),
                SetResult(id = "2", type = SetResult.Type.REGULAR, weight = null, reps = null),
                SetResult(id = "3", type = SetResult.Type.REGULAR, weight = null, reps = null)
            )
        ),
        Exercise(
            id = "2",
            name = "Rozpiętki hantlami na ławce skos dodatni",
            sets = Sets(regular = 2, drop = 1),
            reps = Reps(10..12),
            results = listOf(
                SetResult(id = "4", type = SetResult.Type.REGULAR, weight = null, reps = null),
                SetResult(id = "5", type = SetResult.Type.REGULAR, weight = null, reps = null),
                SetResult(id = "6", type = SetResult.Type.DROP, weight = null, reps = null)
            )
        ),
        Exercise(
            id = "3",
            name = "Wyciskanie hantlami nad głowę siedząc",
            sets = Sets(regular = 2, drop = 1),
            reps = Reps(8..10),
            results = listOf(
                SetResult(id = "7", type = SetResult.Type.REGULAR, weight = null, reps = null),
                SetResult(id = "8", type = SetResult.Type.REGULAR, weight = null, reps = null),
                SetResult(id = "9", type = SetResult.Type.DROP, weight = null, reps = null)
            )
        ),
        Exercise(
            id = "4",
            name = "Wznosy hantli bokiem stojąc",
            sets = Sets(regular = 1, drop = 3),
            reps = Reps(10..20),
            results = listOf(
                SetResult(id = "10", type = SetResult.Type.REGULAR, weight = null, reps = null),
                SetResult(id = "11", type = SetResult.Type.DROP, weight = null, reps = null),
                SetResult(id = "12", type = SetResult.Type.DROP, weight = null, reps = null),
                SetResult(id = "13", type = SetResult.Type.DROP, weight = null, reps = null)
            )
        ),
        Exercise(
            id = "5",
            name = "Prostowanie ramion na wyciągu",
            sets = Sets(regular = 2, drop = 1),
            reps = Reps(10..12),
            results = listOf(
                SetResult(id = "14", type = SetResult.Type.REGULAR, weight = null, reps = null),
                SetResult(id = "15", type = SetResult.Type.REGULAR, weight = null, reps = null),
                SetResult(id = "16", type = SetResult.Type.DROP, weight = null, reps = null)
            )
        )
    )
)
