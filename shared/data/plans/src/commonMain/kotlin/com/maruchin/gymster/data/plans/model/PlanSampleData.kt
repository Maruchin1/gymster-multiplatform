package com.maruchin.gymster.data.plans.model

val samplePlans = listOf(
    Plan(
        id = "1",
        name = "Push Pull Legs",
        days = listOf(
            PlanDay(
                id = "1",
                name = "Push",
                exercises = listOf(
                    PlanExercise(
                        id = "1",
                        name = "Wyciskanie sztangi na ławce poziomej",
                        sets = Sets(regular = 3),
                        reps = Reps(4..6)
                    ),
                    PlanExercise(
                        id = "2",
                        name = "Rozpiętki hantlami na ławce skos dodatni",
                        sets = Sets(regular = 2, drop = 1),
                        reps = Reps(10..12)
                    ),
                    PlanExercise(
                        id = "3",
                        name = "Wyciskanie hantlami nad głowę siedząc",
                        sets = Sets(regular = 2, drop = 1),
                        reps = Reps(8..10)
                    ),
                    PlanExercise(
                        id = "4",
                        name = "Wznosy hantli bokiem stojąc",
                        sets = Sets(regular = 1, drop = 3),
                        reps = Reps(10..20)
                    ),
                    PlanExercise(
                        id = "5",
                        name = "Prostowanie ramion na wyciągu",
                        sets = Sets(regular = 2, drop = 1),
                        reps = Reps(10..12)
                    )
                )
            ),
            PlanDay(
                id = "2",
                name = "Pull",
                exercises = listOf(
                    PlanExercise(
                        id = "6",
                        name = "Wiosłowanie sztangą",
                        sets = Sets(regular = 3),
                        reps = Reps(6..8)
                    ),
                    PlanExercise(
                        id = "7",
                        name = "Ściąganie drążka wyciągu górnego chwytem V",
                        sets = Sets(regular = 3),
                        reps = Reps(8..10)
                    ),
                    PlanExercise(
                        id = "8",
                        name = "Wisoławanie hantlami w oparciu o ławkę",
                        sets = Sets(regular = 2),
                        reps = Reps(10..12)
                    ),
                    PlanExercise(
                        id = "9",
                        name = "Odwrotne rozpiętki hantlami w oparciu o ławkę",
                        sets = Sets(regular = 1, drop = 2),
                        reps = Reps(10..20)
                    ),
                    PlanExercise(
                        id = "10",
                        name = "Uginanie hantli na modlitewniku",
                        sets = Sets(regular = 2, drop = 1),
                        reps = Reps(10..12)
                    )
                )
            ),
            PlanDay(
                id = "3",
                name = "Legs",
                exercises = listOf(
                    PlanExercise(
                        id = "11",
                        name = "Przysiad ze sztangą z tyłu",
                        sets = Sets(regular = 3),
                        reps = Reps(4..6)
                    ),
                    PlanExercise(
                        id = "12",
                        name = "Wypychanie nóg na suwnicy",
                        sets = Sets(regular = 3),
                        reps = Reps(8..10)
                    ),
                    PlanExercise(
                        id = "13",
                        name = "Uginanie nóg leżąc na maszynie",
                        sets = Sets(regular = 2, drop = 1),
                        reps = Reps(10..12)
                    ),
                    PlanExercise(
                        id = "14",
                        name = "Wyprosty tułowia na ławce rzymskiej",
                        sets = Sets(regular = 2, drop = 1),
                        reps = Reps(10..12)
                    ),
                    PlanExercise(
                        id = "15",
                        name = "Wspięcia na palce na suwnicy",
                        sets = Sets(regular = 1, drop = 2),
                        reps = Reps(10..20)
                    )
                )
            )
        )
    ),
    Plan(id = "2", name = "Full Body Workout", days = emptyList())
)
