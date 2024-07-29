package com.maruchin.gymster.data.plans.model

val samplePlans = listOf(
    Plan(
        id = "1",
        name = "Push Pull",
        days = listOf(
            PlanDay(
                id = "1",
                name = "Push 1",
                exercises = listOf(
                    PlanExercise(
                        id = "1",
                        name = "Bench Press",
                        sets = Sets(regular = 3),
                        reps = Reps(4..6)
                    ),
                    PlanExercise(
                        id = "2",
                        name = "Overhead Press",
                        sets = Sets(regular = 3),
                        reps = Reps(10..12)
                    ),
                    PlanExercise(
                        id = "3",
                        name = "Triceps Extension",
                        sets = Sets(regular = 1, drop = 2),
                        reps = Reps(10..20)
                    )
                )
            ),
            PlanDay(
                id = "2",
                name = "Pull 1",
                exercises = emptyList()
            ),
            PlanDay(
                id = "3",
                name = "Push 2",
                exercises = emptyList()
            ),
            PlanDay(
                id = "4",
                name = "Pull 2",
                exercises = emptyList()
            )
        )
    ),
    Plan(id = "2", name = "Full Body Workout", days = emptyList())
)
