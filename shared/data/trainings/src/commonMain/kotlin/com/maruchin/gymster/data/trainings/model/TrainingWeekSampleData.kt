package com.maruchin.gymster.data.trainings.model

import kotlinx.datetime.LocalDate

val sampleTrainingWeeks = listOf(
    TrainingWeek(
        startDate = LocalDate(2024, 10, 7),
        endDate = LocalDate(2024, 10, 13),
        trainings = listOf(samplePullTraining)
    ),
    TrainingWeek(
        startDate = LocalDate(2024, 9, 30),
        endDate = LocalDate(2024, 10, 6),
        trainings = listOf(samplePushTraining)
    )
)
