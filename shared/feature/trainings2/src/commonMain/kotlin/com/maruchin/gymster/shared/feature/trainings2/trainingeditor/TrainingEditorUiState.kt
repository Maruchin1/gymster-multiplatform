package com.maruchin.gymster.shared.feature.trainings2.trainingeditor

import com.maruchin.gymster.data.trainings2.model.Training

data class TrainingEditorUiState(
    val training: Training? = null,
    val previousTraining: Training? = null
)
