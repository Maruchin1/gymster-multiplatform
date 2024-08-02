package com.maruchin.gymster.feature.trainings.trainingeditor

import com.maruchin.gymster.data.trainings.model.Training

sealed interface TrainingEditorUiState {

    data object Loading : TrainingEditorUiState

    data class Loaded(val training: Training) : TrainingEditorUiState
}
