package com.maruchin.multiplatform.gymster.shared.data.trainingplans.model

data class TrainingPlanDay(
    val id: String,
    val name: String,
    val exercises: List<TrainingPlanExercise>
)
