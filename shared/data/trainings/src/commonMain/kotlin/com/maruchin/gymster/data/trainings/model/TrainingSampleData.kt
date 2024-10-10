package com.maruchin.gymster.data.trainings.model

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

val samplePullTraining = Training(
    id = "2",
    name = "Pull",
    planName = "Push Pull Legs",
    date = LocalDate(2024, 10, 8),
    isComplete = false,
    exercises = listOf(
        Exercise(
            id = "6",
            name = "Wiosłowanie sztangą",
            sets = Sets(regular = 3),
            reps = Reps(6..8),
            results = listOf(
                SetResult(id = "17", type = SetResult.Type.REGULAR, weight = null, reps = null),
                SetResult(id = "18", type = SetResult.Type.REGULAR, weight = null, reps = null),
                SetResult(id = "19", type = SetResult.Type.REGULAR, weight = null, reps = null)
            )
        ),
        Exercise(
            id = "7",
            name = "Ściąganie drążka wyciągu górnego chwytem V",
            sets = Sets(regular = 3),
            reps = Reps(8..10),
            results = listOf(
                SetResult(id = "20", type = SetResult.Type.REGULAR, weight = null, reps = null),
                SetResult(id = "21", type = SetResult.Type.REGULAR, weight = null, reps = null),
                SetResult(id = "22", type = SetResult.Type.REGULAR, weight = null, reps = null)
            )
        ),
        Exercise(
            id = "8",
            name = "Wisoławanie hantlami w oparciu o ławkę",
            sets = Sets(regular = 2),
            reps = Reps(10..12),
            results = listOf(
                SetResult(id = "23", type = SetResult.Type.REGULAR, weight = null, reps = null),
                SetResult(id = "24", type = SetResult.Type.REGULAR, weight = null, reps = null)
            )
        ),
        Exercise(
            id = "9",
            name = "Odwrotne rozpiętki hantlami w oparciu o ławkę",
            sets = Sets(regular = 1, drop = 2),
            reps = Reps(10..20),
            results = listOf(
                SetResult(id = "25", type = SetResult.Type.REGULAR, weight = null, reps = null),
                SetResult(id = "26", type = SetResult.Type.DROP, weight = null, reps = null),
                SetResult(id = "27", type = SetResult.Type.DROP, weight = null, reps = null)
            )
        ),
        Exercise(
            id = "10",
            name = "Uginanie hantli na modlitewniku",
            sets = Sets(regular = 2, drop = 1),
            reps = Reps(10..12),
            results = listOf(
                SetResult(id = "28", type = SetResult.Type.REGULAR, weight = null, reps = null),
                SetResult(id = "29", type = SetResult.Type.REGULAR, weight = null, reps = null),
                SetResult(id = "30", type = SetResult.Type.DROP, weight = null, reps = null)
            )
        )
    )
)

val sampleTrainings = listOf(samplePullTraining, samplePushTraining)
