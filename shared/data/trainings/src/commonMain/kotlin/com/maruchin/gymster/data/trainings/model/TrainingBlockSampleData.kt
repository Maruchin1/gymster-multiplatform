package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import kotlinx.datetime.LocalDate

val sampleTrainingBlocks = listOf(
    TrainingBlock(
        id = "1",
        planName = "Push Pull Legs",
        startDate = LocalDate(2024, 8, 12),
        weeks = listOf(
            TrainingWeek(
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
                                progress = listOf(
                                    SetProgress(
                                        id = "1",
                                        type = SetProgress.Type.REGULAR,
                                        progress = Progress(70.0 to 5)
                                    ),
                                    SetProgress(
                                        id = "2",
                                        type = SetProgress.Type.REGULAR,
                                        progress = Progress(70.0 to 4)
                                    ),
                                    SetProgress(
                                        id = "3",
                                        type = SetProgress.Type.REGULAR,
                                        progress = Progress(70.0 to 4)
                                    )
                                )
                            ),
                            Exercise(
                                id = "2",
                                name = "Rozpiętki hantlami na ławce skos dodatni",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                progress = listOf(
                                    SetProgress(
                                        id = "4",
                                        type = SetProgress.Type.REGULAR,
                                        progress = Progress(10.0 to 12)
                                    ),
                                    SetProgress(
                                        id = "5",
                                        type = SetProgress.Type.REGULAR,
                                        progress = Progress(10.0 to 12)
                                    ),
                                    SetProgress(
                                        id = "6",
                                        type = SetProgress.Type.DROP,
                                        progress = Progress(5.0 to 12)
                                    )
                                )
                            ),
                            Exercise(
                                id = "3",
                                name = "Wyciskanie hantlami nad głowę siedząc",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(8..10),
                                progress = listOf(
                                    SetProgress(
                                        id = "7",
                                        type = SetProgress.Type.REGULAR,
                                        progress = Progress(15.0 to 10)
                                    ),
                                    SetProgress(
                                        id = "8",
                                        type = SetProgress.Type.REGULAR,
                                        progress = Progress(15.0 to 10)
                                    ),
                                    SetProgress(
                                        id = "9",
                                        type = SetProgress.Type.DROP,
                                        progress = Progress(15.0 to 10)
                                    )
                                )
                            ),
                            Exercise(
                                id = "4",
                                name = "Wznosy hantli bokiem stojąc",
                                sets = Sets(regular = 1, drop = 3),
                                reps = Reps(10..20),
                                progress = listOf(
                                    SetProgress(
                                        id = "10",
                                        type = SetProgress.Type.REGULAR,
                                        progress = Progress(7.5 to 15)
                                    ),
                                    SetProgress(
                                        id = "11",
                                        type = SetProgress.Type.DROP,
                                        progress = Progress(5.0 to 15)
                                    ),
                                    SetProgress(
                                        id = "12",
                                        type = SetProgress.Type.DROP,
                                        progress = Progress(3.0 to 14)
                                    ),
                                    SetProgress(
                                        id = "13",
                                        type = SetProgress.Type.DROP,
                                        progress = Progress(2.0 to 14)
                                    )
                                )
                            ),
                            Exercise(
                                id = "5",
                                name = "Prostowanie ramion na wyciągu",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                progress = listOf(
                                    SetProgress(
                                        id = "14",
                                        type = SetProgress.Type.REGULAR,
                                        progress = Progress(20.0 to 12)
                                    ),
                                    SetProgress(
                                        id = "15",
                                        type = SetProgress.Type.REGULAR,
                                        progress = Progress(20.0 to 12)
                                    ),
                                    SetProgress(
                                        id = "16",
                                        type = SetProgress.Type.DROP,
                                        progress = Progress(20.0 to 12)
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
                                listOf(
                                    SetProgress(
                                        id = "17",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "18",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "19",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "7",
                                name = "Ściąganie drążka wyciągu górnego chwytem V",
                                sets = Sets(regular = 3),
                                reps = Reps(8..10),
                                listOf(
                                    SetProgress(
                                        id = "20",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "21",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "22",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "8",
                                name = "Wisoławanie hantlami w oparciu o ławkę",
                                sets = Sets(regular = 2),
                                reps = Reps(10..12),
                                listOf(
                                    SetProgress(
                                        id = "23",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "24",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "9",
                                name = "Odwrotne rozpiętki hantlami w oparciu o ławkę",
                                sets = Sets(regular = 1, drop = 2),
                                reps = Reps(10..20),
                                listOf(
                                    SetProgress(
                                        id = "25",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "26",
                                        type = SetProgress.Type.DROP,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "27",
                                        type = SetProgress.Type.DROP,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "10",
                                name = "Uginanie hantli na modlitewniku",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                listOf(
                                    SetProgress(
                                        id = "28",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "29",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "30",
                                        type = SetProgress.Type.DROP,
                                        progress = null
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
                                listOf(
                                    SetProgress(
                                        id = "31",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "32",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "33",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "12",
                                name = "Wypychanie nóg na suwnicy",
                                sets = Sets(regular = 3),
                                reps = Reps(8..10),
                                listOf(
                                    SetProgress(
                                        id = "34",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "35",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "36",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "13",
                                name = "Uginanie nóg leżąc na maszynie",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                listOf(
                                    SetProgress(
                                        id = "37",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "38",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "39",
                                        type = SetProgress.Type.DROP,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "14",
                                name = "Wyprosty tułowia na ławce rzymskiej",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                listOf(
                                    SetProgress(
                                        id = "40",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "41",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "42",
                                        type = SetProgress.Type.DROP,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "15",
                                name = "Wspięcia na palce na suwnicy",
                                sets = Sets(regular = 1, drop = 2),
                                reps = Reps(10..20),
                                listOf(
                                    SetProgress(
                                        id = "43",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "44",
                                        type = SetProgress.Type.DROP,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "45",
                                        type = SetProgress.Type.DROP,
                                        progress = null
                                    )
                                )
                            )
                        )
                    )
                )
            )
        ) + (2..8).map { weekNumber ->
            TrainingWeek(
                trainings = listOf(
                    Training(
                        id = "${weekNumber}1",
                        name = "Push",
                        exercises = listOf(
                            Exercise(
                                id = "${weekNumber}1",
                                name = "Wyciskanie sztangi na ławce poziomej",
                                sets = Sets(regular = 3),
                                reps = Reps(4..6),
                                progress = listOf(
                                    SetProgress(
                                        id = "${weekNumber}46",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}47",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}48",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "${weekNumber}2",
                                name = "Rozpiętki hantlami na ławce skos dodatni",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                progress = listOf(
                                    SetProgress(
                                        id = "${weekNumber}49",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}50",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}51",
                                        type = SetProgress.Type.DROP,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "${weekNumber}3",
                                name = "Wyciskanie hantlami nad głowę siedząc",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(8..10),
                                progress = listOf(
                                    SetProgress(
                                        id = "${weekNumber}52",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}53",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}54",
                                        type = SetProgress.Type.DROP,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "${weekNumber}4",
                                name = "Wznosy hantli bokiem stojąc",
                                sets = Sets(regular = 1, drop = 3),
                                reps = Reps(10..20),
                                progress = listOf(
                                    SetProgress(
                                        id = "${weekNumber}55",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}56",
                                        type = SetProgress.Type.DROP,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}57",
                                        type = SetProgress.Type.DROP,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}58",
                                        type = SetProgress.Type.DROP,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "${weekNumber}5",
                                name = "Prostowanie ramion na wyciągu",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                progress = listOf(
                                    SetProgress(
                                        id = "${weekNumber}59",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}60",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}61",
                                        type = SetProgress.Type.DROP,
                                        progress = null
                                    )
                                )
                            )
                        )
                    ),
                    Training(
                        id = "${weekNumber}2",
                        name = "Pull",
                        exercises = listOf(
                            Exercise(
                                id = "${weekNumber}6",
                                name = "Wiosłowanie sztangą",
                                sets = Sets(regular = 3),
                                reps = Reps(6..8),
                                listOf(
                                    SetProgress(
                                        id = "${weekNumber}62",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}63",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}64",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "${weekNumber}7",
                                name = "Ściąganie drążka wyciągu górnego chwytem V",
                                sets = Sets(regular = 3),
                                reps = Reps(8..10),
                                listOf(
                                    SetProgress(
                                        id = "${weekNumber}65",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}66",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}67",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "${weekNumber}8",
                                name = "Wisoławanie hantlami w oparciu o ławkę",
                                sets = Sets(regular = 2),
                                reps = Reps(10..12),
                                listOf(
                                    SetProgress(
                                        id = "${weekNumber}68",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}69",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "${weekNumber}9",
                                name = "Odwrotne rozpiętki hantlami w oparciu o ławkę",
                                sets = Sets(regular = 1, drop = 2),
                                reps = Reps(10..20),
                                listOf(
                                    SetProgress(
                                        id = "${weekNumber}70",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}71",
                                        type = SetProgress.Type.DROP,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}72",
                                        type = SetProgress.Type.DROP,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "${weekNumber}10",
                                name = "Uginanie hantli na modlitewniku",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                listOf(
                                    SetProgress(
                                        id = "${weekNumber}73",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}74",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}75",
                                        type = SetProgress.Type.DROP,
                                        progress = null
                                    )
                                )
                            )
                        )
                    ),
                    Training(
                        id = "${weekNumber}3",
                        name = "Legs",
                        exercises = listOf(
                            Exercise(
                                id = "${weekNumber}11",
                                name = "Przysiad ze sztangą z tyłu",
                                sets = Sets(regular = 3),
                                reps = Reps(4..6),
                                listOf(
                                    SetProgress(
                                        id = "${weekNumber}76",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}77",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}78",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "${weekNumber}12",
                                name = "Wypychanie nóg na suwnicy",
                                sets = Sets(regular = 3),
                                reps = Reps(8..10),
                                listOf(
                                    SetProgress(
                                        id = "${weekNumber}79",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}80",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}81",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "${weekNumber}13",
                                name = "Uginanie nóg leżąc na maszynie",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                listOf(
                                    SetProgress(
                                        id = "${weekNumber}82",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}83",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}84",
                                        type = SetProgress.Type.DROP,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "${weekNumber}14",
                                name = "Wyprosty tułowia na ławce rzymskiej",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                listOf(
                                    SetProgress(
                                        id = "${weekNumber}85",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}86",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}87",
                                        type = SetProgress.Type.DROP,
                                        progress = null
                                    )
                                )
                            ),
                            Exercise(
                                id = "${weekNumber}15",
                                name = "Wspięcia na palce na suwnicy",
                                sets = Sets(regular = 1, drop = 2),
                                reps = Reps(10..20),
                                listOf(
                                    SetProgress(
                                        id = "${weekNumber}88",
                                        type = SetProgress.Type.REGULAR,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}89",
                                        type = SetProgress.Type.DROP,
                                        progress = null
                                    ),
                                    SetProgress(
                                        id = "${weekNumber}90",
                                        type = SetProgress.Type.DROP,
                                        progress = null
                                    )
                                )
                            )
                        )
                    )
                )
            )
        }
    )
)
