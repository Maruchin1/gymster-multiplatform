package com.maruchin.multiplatform.gymster.shared.data.trainingplans.model

val sampleTrainingPlans = listOf(
    TrainingPlan(
        id = "1",
        name = "Push Pull",
        days = listOf(
            TrainingPlanDay(
                id = "1",
                name = "Push 1",
                exercises = listOf(
                    TrainingPlanExercise(
                        id = "1",
                        name = "Bench Press",
                        sets = Sets(regular = 3),
                        reps = Reps(4..6)
                    ),
                    TrainingPlanExercise(
                        id = "2",
                        name = "Overhead Press",
                        sets = Sets(regular = 3),
                        reps = Reps(10..12)
                    ),
                    TrainingPlanExercise(
                        id = "3",
                        name = "Triceps Extension",
                        sets = Sets(regular = 1, drop = 2),
                        reps = Reps(10..20)
                    )
                )
            ),
            TrainingPlanDay(
                id = "2",
                name = "Pull 1",
                exercises = emptyList()
            ),
            TrainingPlanDay(
                id = "3",
                name = "Push 2",
                exercises = emptyList()
            ),
            TrainingPlanDay(
                id = "4",
                name = "Pull 2",
                exercises = emptyList()
            )
        )
    ),
    TrainingPlan(id = "2", name = "Full Body Workout", days = emptyList())
)
