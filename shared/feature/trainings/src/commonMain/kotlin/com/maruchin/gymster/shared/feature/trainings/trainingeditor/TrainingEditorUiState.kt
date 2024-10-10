package com.maruchin.gymster.shared.feature.trainings.trainingeditor

import com.maruchin.gymster.data.trainings.model.Training

data class TrainingEditorUiState(
    val training: Training? = null,
    val previousTraining: Training? = null
)
