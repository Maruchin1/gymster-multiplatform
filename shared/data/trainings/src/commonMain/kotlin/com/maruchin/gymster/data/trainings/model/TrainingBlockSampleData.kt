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
                                    Progress(70.0 to 5),
                                    Progress(70.0 to 4),
                                    Progress(70.0 to 4)
                                )
                            ),
                            Exercise(
                                id = "2",
                                name = "Rozpiętki hantlami na ławce skos dodatni",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                progress = listOf(
                                    Progress(10.0 to 12),
                                    Progress(10.0 to 12),
                                    Progress(5.0 to 12)
                                )
                            ),
                            Exercise(
                                id = "3",
                                name = "Wyciskanie hantlami nad głowę siedząc",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(8..10),
                                progress = listOf(
                                    Progress(15.0 to 10),
                                    Progress(15.0 to 10),
                                    Progress(15.0 to 10)
                                )
                            ),
                            Exercise(
                                id = "4",
                                name = "Wznosy hantli bokiem stojąc",
                                sets = Sets(regular = 1, drop = 3),
                                reps = Reps(10..20),
                                progress = listOf(
                                    Progress(7.5 to 15),
                                    Progress(5.0 to 15),
                                    Progress(3.0 to 14),
                                    Progress(2.0 to 14)
                                )
                            ),
                            Exercise(
                                id = "5",
                                name = "Prostowanie ramion na wyciągu",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                progress = listOf(
                                    Progress(20.0 to 12),
                                    Progress(20.0 to 12),
                                    Progress(20.0 to 12)
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
                                listOf(Progress.empty, Progress.empty, Progress.empty)
                            ),
                            Exercise(
                                id = "7",
                                name = "Ściąganie drążka wyciągu górnego chwytem V",
                                sets = Sets(regular = 3),
                                reps = Reps(8..10),
                                listOf(Progress.empty, Progress.empty, Progress.empty)
                            ),
                            Exercise(
                                id = "8",
                                name = "Wisoławanie hantlami w oparciu o ławkę",
                                sets = Sets(regular = 2),
                                reps = Reps(10..12),
                                listOf(Progress.empty, Progress.empty)
                            ),
                            Exercise(
                                id = "9",
                                name = "Odwrotne rozpiętki hantlami w oparciu o ławkę",
                                sets = Sets(regular = 1, drop = 2),
                                reps = Reps(10..20),
                                listOf(Progress.empty, Progress.empty, Progress.empty)
                            ),
                            Exercise(
                                id = "10",
                                name = "Uginanie hantli na modlitewniku",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                listOf(
                                    Progress.empty,
                                    Progress.empty,
                                    Progress.empty
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
                                listOf(Progress.empty, Progress.empty, Progress.empty)
                            ),
                            Exercise(
                                id = "12",
                                name = "Wypychanie nóg na suwnicy",
                                sets = Sets(regular = 3),
                                reps = Reps(8..10),
                                listOf(Progress.empty, Progress.empty, Progress.empty)
                            ),
                            Exercise(
                                id = "13",
                                name = "Uginanie nóg leżąc na maszynie",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                listOf(Progress.empty, Progress.empty, Progress.empty)
                            ),
                            Exercise(
                                id = "14",
                                name = "Wyprosty tułowia na ławce rzymskiej",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                listOf(Progress.empty, Progress.empty, Progress.empty)
                            ),
                            Exercise(
                                id = "15",
                                name = "Wspięcia na palce na suwnicy",
                                sets = Sets(regular = 1, drop = 2),
                                reps = Reps(10..20),
                                listOf(Progress.empty, Progress.empty, Progress.empty)
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
                                progress = listOf(Progress.empty, Progress.empty, Progress.empty)
                            ),
                            Exercise(
                                id = "${weekNumber}2",
                                name = "Rozpiętki hantlami na ławce skos dodatni",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                progress = listOf(Progress.empty, Progress.empty, Progress.empty)
                            ),
                            Exercise(
                                id = "${weekNumber}3",
                                name = "Wyciskanie hantlami nad głowę siedząc",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(8..10),
                                progress = listOf(Progress.empty, Progress.empty, Progress.empty)
                            ),
                            Exercise(
                                id = "${weekNumber}4",
                                name = "Wznosy hantli bokiem stojąc",
                                sets = Sets(regular = 1, drop = 3),
                                reps = Reps(10..20),
                                progress = listOf(
                                    Progress.empty,
                                    Progress.empty,
                                    Progress.empty,
                                    Progress.empty
                                )
                            ),
                            Exercise(
                                id = "${weekNumber}5",
                                name = "Prostowanie ramion na wyciągu",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                progress = listOf(Progress.empty, Progress.empty, Progress.empty)
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
                                listOf(Progress.empty, Progress.empty, Progress.empty)
                            ),
                            Exercise(
                                id = "${weekNumber}7",
                                name = "Ściąganie drążka wyciągu górnego chwytem V",
                                sets = Sets(regular = 3),
                                reps = Reps(8..10),
                                listOf(Progress.empty, Progress.empty, Progress.empty)
                            ),
                            Exercise(
                                id = "${weekNumber}8",
                                name = "Wisoławanie hantlami w oparciu o ławkę",
                                sets = Sets(regular = 2),
                                reps = Reps(10..12),
                                listOf(Progress.empty, Progress.empty)
                            ),
                            Exercise(
                                id = "${weekNumber}9",
                                name = "Odwrotne rozpiętki hantlami w oparciu o ławkę",
                                sets = Sets(regular = 1, drop = 2),
                                reps = Reps(10..20),
                                listOf(Progress.empty, Progress.empty, Progress.empty)
                            ),
                            Exercise(
                                id = "${weekNumber}10",
                                name = "Uginanie hantli na modlitewniku",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                listOf(
                                    Progress.empty,
                                    Progress.empty,
                                    Progress.empty
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
                                listOf(Progress.empty, Progress.empty, Progress.empty)
                            ),
                            Exercise(
                                id = "${weekNumber}12",
                                name = "Wypychanie nóg na suwnicy",
                                sets = Sets(regular = 3),
                                reps = Reps(8..10),
                                listOf(Progress.empty, Progress.empty, Progress.empty)
                            ),
                            Exercise(
                                id = "${weekNumber}13",
                                name = "Uginanie nóg leżąc na maszynie",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                listOf(Progress.empty, Progress.empty, Progress.empty)
                            ),
                            Exercise(
                                id = "${weekNumber}14",
                                name = "Wyprosty tułowia na ławce rzymskiej",
                                sets = Sets(regular = 2, drop = 1),
                                reps = Reps(10..12),
                                listOf(Progress.empty, Progress.empty, Progress.empty)
                            ),
                            Exercise(
                                id = "${weekNumber}15",
                                name = "Wspięcia na palce na suwnicy",
                                sets = Sets(regular = 1, drop = 2),
                                reps = Reps(10..20),
                                listOf(Progress.empty, Progress.empty, Progress.empty)
                            )
                        )
                    )
                )
            )
        }
    )
)
