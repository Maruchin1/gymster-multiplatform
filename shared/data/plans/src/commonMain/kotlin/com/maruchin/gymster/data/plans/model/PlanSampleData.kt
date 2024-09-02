package com.maruchin.gymster.data.plans.model

val samplePlans = listOf(
    Plan(
        id = "1",
        name = "Push Pull Legs",
        trainings = (1..8).map { weekIndex ->
            listOf(
                PlannedTraining(
                    id = "$weekIndex-1",
                    name = "Push",
                    weekIndex = weekIndex,
                    exercises = listOf(
                        PlannedExercise(
                            id = "$weekIndex-1",
                            name = "Wyciskanie sztangi na ławce poziomej",
                            sets = Sets(regular = 3),
                            reps = Reps(4..6)
                        ),
                        PlannedExercise(
                            id = "$weekIndex-2",
                            name = "Rozpiętki hantlami na ławce skos dodatni",
                            sets = Sets(regular = 2, drop = 1),
                            reps = Reps(10..12)
                        ),
                        PlannedExercise(
                            id = "$weekIndex-3",
                            name = "Wyciskanie hantlami nad głowę siedząc",
                            sets = Sets(regular = 2, drop = 1),
                            reps = Reps(8..10)
                        ),
                        PlannedExercise(
                            id = "$weekIndex-4",
                            name = "Wznosy hantli bokiem stojąc",
                            sets = Sets(regular = 1, drop = 3),
                            reps = Reps(10..20)
                        ),
                        PlannedExercise(
                            id = "$weekIndex-5",
                            name = "Prostowanie ramion na wyciągu",
                            sets = Sets(regular = 2, drop = 1),
                            reps = Reps(10..12)
                        )
                    )
                ),
                PlannedTraining(
                    id = "$weekIndex-2",
                    name = "Pull",
                    weekIndex = weekIndex,
                    exercises = listOf(
                        PlannedExercise(
                            id = "$weekIndex-6",
                            name = "Wiosłowanie sztangą",
                            sets = Sets(regular = 3),
                            reps = Reps(6..8)
                        ),
                        PlannedExercise(
                            id = "$weekIndex-7",
                            name = "Ściąganie drążka wyciągu górnego chwytem V",
                            sets = Sets(regular = 3),
                            reps = Reps(8..10)
                        ),
                        PlannedExercise(
                            id = "$weekIndex-8",
                            name = "Wisoławanie hantlami w oparciu o ławkę",
                            sets = Sets(regular = 2),
                            reps = Reps(10..12)
                        ),
                        PlannedExercise(
                            id = "$weekIndex-9",
                            name = "Odwrotne rozpiętki hantlami w oparciu o ławkę",
                            sets = Sets(regular = 1, drop = 2),
                            reps = Reps(10..20)
                        ),
                        PlannedExercise(
                            id = "$weekIndex-10",
                            name = "Uginanie hantli na modlitewniku",
                            sets = Sets(regular = 2, drop = 1),
                            reps = Reps(10..12)
                        )
                    )
                ),
                PlannedTraining(
                    id = "$weekIndex-3",
                    name = "Legs",
                    weekIndex = weekIndex,
                    exercises = listOf(
                        PlannedExercise(
                            id = "$weekIndex-11",
                            name = "Przysiad ze sztangą z tyłu",
                            sets = Sets(regular = 3),
                            reps = Reps(4..6)
                        ),
                        PlannedExercise(
                            id = "$weekIndex-12",
                            name = "Wypychanie nóg na suwnicy",
                            sets = Sets(regular = 3),
                            reps = Reps(8..10)
                        ),
                        PlannedExercise(
                            id = "$weekIndex-13",
                            name = "Uginanie nóg leżąc na maszynie",
                            sets = Sets(regular = 2, drop = 1),
                            reps = Reps(10..12)
                        ),
                        PlannedExercise(
                            id = "$weekIndex-14",
                            name = "Wyprosty tułowia na ławce rzymskiej",
                            sets = Sets(regular = 2, drop = 1),
                            reps = Reps(10..12)
                        ),
                        PlannedExercise(
                            id = "$weekIndex-15",
                            name = "Wspięcia na palce na suwnicy",
                            sets = Sets(regular = 1, drop = 2),
                            reps = Reps(10..20)
                        )
                    )
                )
            )
        }.flatten()
    ),
    Plan(id = "2", name = "Full Body Workout", trainings = emptyList())
)
