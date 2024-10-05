package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.core.utils.updated
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import kotlinx.datetime.LocalDate

val sampleTrainingBlocks = listOf(
    TrainingBlock(
        id = "1",
        planName = "Push Pull Legs",
        startDate = LocalDate(2024, 8, 12),
        isActive = false,
        weeks = listOf(
            TrainingWeek(
                isComplete = false,
                trainings = listOf(
                    Training(
                        id = "1",
                        name = "Push",
                        exercises = listOf(
                            Exercise(
                                id = "1",
                                name = "Wyciskanie sztangi na ławce poziomej",
                                sets = Sets(regular = 3),
                                reps = Reps(4..6),
                                results = listOf(
                                    SetResult(
                                        id = "1",
                                        type = SetResult.Type.REGULAR,
                                        weight = 70.0,
                                        reps = 5
                                    ),
                                    SetResult(
                                        id = "2",
                                        type = SetResult.Type.REGULAR,
                                        weight = 70.0,
                                        reps = 4
                                    ),
                                    SetResult(
                                        id = "3",
                                        type = SetResult.Type.REGULAR,
                                        weight = 70.0,
                                        reps = 4
                                    )
                                )
                            ),
                            Exercise(
                                id = "2",
                                name = "Rozpiętki hantlami na ławce skos dodatni",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                results = listOf(
                                    SetResult(
                                        id = "4",
                                        type = SetResult.Type.REGULAR,
                                        weight = 10.0,
                                        reps = 12
                                    ),
                                    SetResult(
                                        id = "5",
                                        type = SetResult.Type.REGULAR,
                                        weight = 10.0,
                                        reps = 12
                                    ),
                                    SetResult(
                                        id = "6",
                                        type = SetResult.Type.DROP,
                                        weight = 5.0,
                                        reps = 12
                                    )
                                )
                            ),
                            Exercise(
                                id = "3",
                                name = "Wyciskanie hantlami nad głowę siedząc",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(8..10),
                                results = listOf(
                                    SetResult(
                                        id = "7",
                                        type = SetResult.Type.REGULAR,
                                        weight = 15.0,
                                        reps = 10
                                    ),
                                    SetResult(
                                        id = "8",
                                        type = SetResult.Type.REGULAR,
                                        weight = 15.0,
                                        reps = 10
                                    ),
                                    SetResult(
                                        id = "9",
                                        type = SetResult.Type.DROP,
                                        weight = 15.0,
                                        reps = 10
                                    )
                                )

                            ),
                            Exercise(
                                id = "4",
                                name = "Wznosy hantli bokiem stojąc",
                                sets = Sets(regular = 1, drop = 3),
                                reps = Reps(10..20),
                                results = listOf(
                                    SetResult(
                                        id = "10",
                                        type = SetResult.Type.REGULAR,
                                        weight = 7.0,
                                        reps = 14
                                    ),
                                    SetResult(
                                        id = "11",
                                        type = SetResult.Type.DROP,
                                        weight = 7.0,
                                        reps = 14
                                    ),
                                    SetResult(
                                        id = "12",
                                        type = SetResult.Type.DROP,
                                        weight = 7.0,
                                        reps = 14
                                    ),
                                    SetResult(
                                        id = "13",
                                        type = SetResult.Type.DROP,
                                        weight = 7.0,
                                        reps = 14
                                    )
                                )

                            ),
                            Exercise(
                                id = "5",
                                name = "Prostowanie ramion na wyciągu",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                results = listOf(
                                    SetResult(
                                        id = "14",
                                        type = SetResult.Type.REGULAR,
                                        weight = 30.0,
                                        reps = 11
                                    ),
                                    SetResult(
                                        id = "15",
                                        type = SetResult.Type.REGULAR,
                                        weight = 30.0,
                                        reps = 11
                                    ),
                                    SetResult(
                                        id = "16",
                                        type = SetResult.Type.DROP,
                                        weight = 20.0,
                                        reps = 10
                                    )
                                )

                            )
                        )
                    ),
                    Training(
                        id = "2",
                        name = "Pull",
                        exercises = listOf(
                            Exercise(
                                id = "6",
                                name = "Wiosłowanie sztangą",
                                sets = Sets(regular = 3),
                                reps = Reps(6..8),
                                results = listOf(
                                    SetResult(
                                        id = "17",
                                        type = SetResult.Type.REGULAR,
                                        weight = 60.0,
                                        reps = 8
                                    ),
                                    SetResult(
                                        id = "18",
                                        type = SetResult.Type.REGULAR,
                                        weight = 60.0,
                                        reps = 8
                                    ),
                                    SetResult(
                                        id = "19",
                                        type = SetResult.Type.REGULAR,
                                        weight = 60.0,
                                        reps = 8
                                    )
                                )

                            ),
                            Exercise(
                                id = "7",
                                name = "Ściąganie drążka wyciągu górnego chwytem V",
                                sets = Sets(regular = 3),
                                reps = Reps(8..10),
                                results = listOf(
                                    SetResult(
                                        id = "20",
                                        type = SetResult.Type.REGULAR,
                                        weight = 50.0,
                                        reps = 9
                                    ),
                                    SetResult(
                                        id = "21",
                                        type = SetResult.Type.REGULAR,
                                        weight = 50.0,
                                        reps = 9
                                    ),
                                    SetResult(
                                        id = "22",
                                        type = SetResult.Type.REGULAR,
                                        weight = 50.0,
                                        reps = 9
                                    )
                                )

                            ),
                            Exercise(
                                id = "8",
                                name = "Wisoławanie hantlami w oparciu o ławkę",
                                sets = Sets(regular = 2),
                                reps = Reps(10..12),
                                results = listOf(
                                    SetResult(
                                        id = "23",
                                        type = SetResult.Type.REGULAR,
                                        weight = 25.0,
                                        reps = 11
                                    ),
                                    SetResult(
                                        id = "24",
                                        type = SetResult.Type.REGULAR,
                                        weight = 25.0,
                                        reps = 11
                                    )
                                )

                            ),
                            Exercise(
                                id = "9",
                                name = "Odwrotne rozpiętki hantlami w oparciu o ławkę",
                                sets = Sets(regular = 1, drop = 2),
                                reps = Reps(10..20),
                                results = listOf(
                                    SetResult(
                                        id = "25",
                                        type = SetResult.Type.REGULAR,
                                        weight = 5.0,
                                        reps = 12
                                    ),
                                    SetResult(
                                        id = "26",
                                        type = SetResult.Type.DROP,
                                        weight = 5.0,
                                        reps = 12
                                    ),
                                    SetResult(
                                        id = "27",
                                        type = SetResult.Type.DROP,
                                        weight = 5.0,
                                        reps = 12
                                    )
                                )

                            ),
                            Exercise(
                                id = "10",
                                name = "Uginanie hantli na modlitewniku",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                results = listOf(
                                    SetResult(
                                        id = "28",
                                        type = SetResult.Type.REGULAR,
                                        weight = 10.0,
                                        reps = 11
                                    ),
                                    SetResult(
                                        id = "29",
                                        type = SetResult.Type.REGULAR,
                                        weight = 10.0,
                                        reps = 11
                                    ),
                                    SetResult(
                                        id = "30",
                                        type = SetResult.Type.DROP,
                                        weight = 5.0,
                                        reps = 10
                                    )
                                )

                            )
                        )
                    ),
                    Training(
                        id = "3",
                        name = "Legs",
                        exercises = listOf(
                            Exercise(
                                id = "11",
                                name = "Przysiad ze sztangą z tyłu",
                                sets = Sets(regular = 3),
                                reps = Reps(4..6),
                                results = listOf(
                                    SetResult(
                                        id = "31",
                                        type = SetResult.Type.REGULAR,
                                        weight = 60.0,
                                        reps = 5
                                    ),
                                    SetResult(
                                        id = "32",
                                        type = SetResult.Type.REGULAR,
                                        weight = 60.0,
                                        reps = 5
                                    ),
                                    SetResult(
                                        id = "33",
                                        type = SetResult.Type.REGULAR,
                                        weight = 60.0,
                                        reps = 5
                                    )
                                )

                            ),
                            Exercise(
                                id = "12",
                                name = "Wypychanie nóg na suwnicy",
                                sets = Sets(regular = 3),
                                reps = Reps(8..10),
                                results = listOf(
                                    SetResult(
                                        id = "34",
                                        type = SetResult.Type.REGULAR,
                                        weight = 70.0,
                                        reps = 9
                                    ),
                                    SetResult(
                                        id = "35",
                                        type = SetResult.Type.REGULAR,
                                        weight = 70.0,
                                        reps = 9
                                    ),
                                    SetResult(
                                        id = "36",
                                        type = SetResult.Type.REGULAR,
                                        weight = 70.0,
                                        reps = 9
                                    )
                                )

                            ),
                            Exercise(
                                id = "13",
                                name = "Uginanie nóg leżąc na maszynie",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                results = listOf(
                                    SetResult(
                                        id = "37",
                                        type = SetResult.Type.REGULAR,
                                        weight = 30.0,
                                        reps = 10
                                    ),
                                    SetResult(
                                        id = "38",
                                        type = SetResult.Type.REGULAR,
                                        weight = 30.0,
                                        reps = 10
                                    ),
                                    SetResult(
                                        id = "39",
                                        type = SetResult.Type.DROP,
                                        weight = 15.0,
                                        reps = 10
                                    )
                                )

                            ),
                            Exercise(
                                id = "14",
                                name = "Wyprosty tułowia na ławce rzymskiej",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                results = listOf(
                                    SetResult(
                                        id = "40",
                                        type = SetResult.Type.REGULAR,
                                        weight = 0.0,
                                        reps = 10
                                    ),
                                    SetResult(
                                        id = "41",
                                        type = SetResult.Type.REGULAR,
                                        weight = 0.0,
                                        reps = 10
                                    ),
                                    SetResult(
                                        id = "42",
                                        type = SetResult.Type.DROP,
                                        weight = 0.0,
                                        reps = 10
                                    )
                                )

                            ),
                            Exercise(
                                id = "15",
                                name = "Wspięcia na palce na suwnicy",
                                sets = Sets(regular = 1, drop = 2),
                                reps = Reps(10..20),
                                results = listOf(
                                    SetResult(
                                        id = "43",
                                        type = SetResult.Type.REGULAR,
                                        weight = 50.0,
                                        reps = 12
                                    ),
                                    SetResult(
                                        id = "44",
                                        type = SetResult.Type.DROP,
                                        weight = 50.0,
                                        reps = 12
                                    ),
                                    SetResult(
                                        id = "45",
                                        type = SetResult.Type.DROP,
                                        weight = 50.0,
                                        reps = 12
                                    )
                                )

                            )
                        )
                    )
                )
            ),
            TrainingWeek(
                isComplete = false,
                trainings = listOf(
                    Training(
                        id = "2-1",
                        name = "Push",
                        exercises = listOf(
                            Exercise(
                                id = "2-1",
                                name = "Wyciskanie sztangi na ławce poziomej",
                                sets = Sets(regular = 3),
                                reps = Reps(4..6),
                                results = listOf(
                                    SetResult(
                                        id = "2-1",
                                        type = SetResult.Type.REGULAR,
                                        weight = 70.0,
                                        reps = 5
                                    ),
                                    SetResult(
                                        id = "2-2",
                                        type = SetResult.Type.REGULAR,
                                        weight = 70.0,
                                        reps = 4
                                    ),
                                    SetResult(
                                        id = "2-3",
                                        type = SetResult.Type.REGULAR,
                                        weight = 70.0,
                                        reps = 4
                                    )
                                )
                            ),
                            Exercise(
                                id = "2-2",
                                name = "Rozpiętki hantlami na ławce skos dodatni",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                results = listOf(
                                    SetResult(
                                        id = "2-4",
                                        type = SetResult.Type.REGULAR,
                                        weight = 10.0,
                                        reps = 12
                                    ),
                                    SetResult(
                                        id = "2-5",
                                        type = SetResult.Type.REGULAR,
                                        weight = 10.0,
                                        reps = 12
                                    ),
                                    SetResult(
                                        id = "2-6",
                                        type = SetResult.Type.DROP,
                                        weight = 5.0,
                                        reps = 12
                                    )
                                )
                            ),
                            Exercise(
                                id = "2-2-3",
                                name = "Wyciskanie hantlami nad głowę siedząc",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(8..10),
                                results = listOf(
                                    SetResult(
                                        id = "2-7",
                                        type = SetResult.Type.REGULAR,
                                        weight = 15.0,
                                        reps = 10
                                    ),
                                    SetResult(
                                        id = "2-8",
                                        type = SetResult.Type.REGULAR,
                                        weight = 15.0,
                                        reps = 10
                                    ),
                                    SetResult(
                                        id = "2-9",
                                        type = SetResult.Type.DROP,
                                        weight = 15.0,
                                        reps = 10
                                    )
                                )

                            ),
                            Exercise(
                                id = "2-2-4",
                                name = "Wznosy hantli bokiem stojąc",
                                sets = Sets(regular = 1, drop = 3),
                                reps = Reps(10..20),
                                results = listOf(
                                    SetResult(
                                        id = "2-10",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-11",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-12",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-13",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "2-5",
                                name = "Prostowanie ramion na wyciągu",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                results = listOf(
                                    SetResult(
                                        id = "2-14",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-15",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-16",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            )
                        )
                    ),
                    Training(
                        id = "2-2",
                        name = "Pull",
                        exercises = listOf(
                            Exercise(
                                id = "2-6",
                                name = "Wiosłowanie sztangą",
                                sets = Sets(regular = 3),
                                reps = Reps(6..8),
                                results = listOf(
                                    SetResult(
                                        id = "2-17",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-18",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-19",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "2-7",
                                name = "Ściąganie drążka wyciągu górnego chwytem V",
                                sets = Sets(regular = 3),
                                reps = Reps(8..10),
                                results = listOf(
                                    SetResult(
                                        id = "2-20",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-21",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-22",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "2-8",
                                name = "Wisoławanie hantlami w oparciu o ławkę",
                                sets = Sets(regular = 2),
                                reps = Reps(10..12),
                                results = listOf(
                                    SetResult(
                                        id = "2-23",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-24",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "2-9",
                                name = "Odwrotne rozpiętki hantlami w oparciu o ławkę",
                                sets = Sets(regular = 1, drop = 2),
                                reps = Reps(10..20),
                                results = listOf(
                                    SetResult(
                                        id = "2-25",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-26",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-27",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "2-10",
                                name = "Uginanie hantli na modlitewniku",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                results = listOf(
                                    SetResult(
                                        id = "2-28",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-29",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-30",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            )
                        )
                    ),
                    Training(
                        id = "2-3",
                        name = "Legs",
                        exercises = listOf(
                            Exercise(
                                id = "2-11",
                                name = "Przysiad ze sztangą z tyłu",
                                sets = Sets(regular = 3),
                                reps = Reps(4..6),
                                results = listOf(
                                    SetResult(
                                        id = "2-31",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-32",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-33",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "2-12",
                                name = "Wypychanie nóg na suwnicy",
                                sets = Sets(regular = 3),
                                reps = Reps(8..10),
                                results = listOf(
                                    SetResult(
                                        id = "2-34",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-35",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-36",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "2-13",
                                name = "Uginanie nóg leżąc na maszynie",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                results = listOf(
                                    SetResult(
                                        id = "2-37",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-38",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-39",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "2-14",
                                name = "Wyprosty tułowia na ławce rzymskiej",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                results = listOf(
                                    SetResult(
                                        id = "2-40",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-41",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-42",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "2-15",
                                name = "Wspięcia na palce na suwnicy",
                                sets = Sets(regular = 1, drop = 2),
                                reps = Reps(10..20),
                                results = listOf(
                                    SetResult(
                                        id = "2-43",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-44",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "2-45",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            )
                        )
                    )
                )
            )
        ) + (3..8).map { weekNumber ->
            TrainingWeek(
                isComplete = false,
                trainings = listOf(
                    Training(
                        id = "$weekNumber-1",
                        name = "Push",
                        exercises = listOf(
                            Exercise(
                                id = "$weekNumber-1",
                                name = "Wyciskanie sztangi na ławce poziomej",
                                sets = Sets(regular = 3),
                                reps = Reps(4..6),
                                results = listOf(
                                    SetResult(
                                        id = "$weekNumber-46",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-47",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-48",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "$weekNumber-2",
                                name = "Rozpiętki hantlami na ławce skos dodatni",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                results = listOf(
                                    SetResult(
                                        id = "$weekNumber-49",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-50",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-51",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "$weekNumber-3",
                                name = "Wyciskanie hantlami nad głowę siedząc",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(8..10),
                                results = listOf(
                                    SetResult(
                                        id = "$weekNumber-52",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-53",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-54",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "$weekNumber-4",
                                name = "Wznosy hantli bokiem stojąc",
                                sets = Sets(regular = 1, drop = 3),
                                reps = Reps(10..20),
                                results = listOf(
                                    SetResult(
                                        id = "$weekNumber-55",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-56",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-57",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-58",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "$weekNumber-5",
                                name = "Prostowanie ramion na wyciągu",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                results = listOf(
                                    SetResult(
                                        id = "$weekNumber-59",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-60",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-61",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            )
                        )
                    ),
                    Training(
                        id = "$weekNumber-2",
                        name = "Pull",
                        exercises = listOf(
                            Exercise(
                                id = "$weekNumber-6",
                                name = "Wiosłowanie sztangą",
                                sets = Sets(regular = 3),
                                reps = Reps(6..8),
                                results = listOf(
                                    SetResult(
                                        id = "$weekNumber-62",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-63",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-64",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "$weekNumber-7",
                                name = "Ściąganie drążka wyciągu górnego chwytem V",
                                sets = Sets(regular = 3),
                                reps = Reps(8..10),
                                results = listOf(
                                    SetResult(
                                        id = "$weekNumber-65",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-66",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-67",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "$weekNumber-8",
                                name = "Wisoławanie hantlami w oparciu o ławkę",
                                sets = Sets(regular = 2),
                                reps = Reps(10..12),
                                results = listOf(
                                    SetResult(
                                        id = "$weekNumber-68",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-69",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "$weekNumber-9",
                                name = "Odwrotne rozpiętki hantlami w oparciu o ławkę",
                                sets = Sets(regular = 1, drop = 2),
                                reps = Reps(10..20),
                                results = listOf(
                                    SetResult(
                                        id = "$weekNumber-70",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-71",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-72",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "$weekNumber-10",
                                name = "Uginanie hantli na modlitewniku",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                results = listOf(
                                    SetResult(
                                        id = "$weekNumber-73",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-74",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-75",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            )
                        )
                    ),
                    Training(
                        id = "$weekNumber-3",
                        name = "Legs",
                        exercises = listOf(
                            Exercise(
                                id = "$weekNumber-11",
                                name = "Przysiad ze sztangą z tyłu",
                                sets = Sets(regular = 3),
                                reps = Reps(4..6),
                                results = listOf(
                                    SetResult(
                                        id = "$weekNumber-76",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-77",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-78",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "$weekNumber-12",
                                name = "Wypychanie nóg na suwnicy",
                                sets = Sets(regular = 3),
                                reps = Reps(8..10),
                                results = listOf(
                                    SetResult(
                                        id = "$weekNumber-79",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-80",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-81",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "$weekNumber-13",
                                name = "Uginanie nóg leżąc na maszynie",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                results = listOf(
                                    SetResult(
                                        id = "$weekNumber-82",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-83",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-84",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "$weekNumber-14",
                                name = "Wyprosty tułowia na ławce rzymskiej",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                results = listOf(
                                    SetResult(
                                        id = "$weekNumber-85",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-86",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "${weekNumber}87",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            ),
                            Exercise(
                                id = "$weekNumber-15",
                                name = "Wspięcia na palce na suwnicy",
                                sets = Sets(regular = 1, drop = 2),
                                reps = Reps(10..20),
                                results = listOf(
                                    SetResult(
                                        id = "$weekNumber-88",
                                        type = SetResult.Type.REGULAR,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-89",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    ),
                                    SetResult(
                                        id = "$weekNumber-90",
                                        type = SetResult.Type.DROP,
                                        weight = null,
                                        reps = null
                                    )
                                )

                            )
                        )
                    )
                )
            )
        }
    ),
    TrainingBlock(
        id = "2",
        planName = "Full Body Workout",
        startDate = LocalDate(2024, 10, 7),
        weeks = emptyList(),
        isActive = false
    )
)

val sampleActiveTrainingBlock = sampleTrainingBlocks[0].copy(isActive = true)

val sampleActiveTrainingBlockFirstWeekCompleted = sampleActiveTrainingBlock.copy(
    weeks = sampleActiveTrainingBlock.weeks.updated(index = 0) {
        it.copy(isComplete = true)
    }
)
