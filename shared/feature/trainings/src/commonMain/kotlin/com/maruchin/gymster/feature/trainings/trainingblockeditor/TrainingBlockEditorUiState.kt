package com.maruchin.gymster.feature.trainings.trainingblockeditor

import com.maruchin.gymster.data.trainings.model.TrainingBlock

sealed interface TrainingBlockEditorUiState {

    data object Loading : TrainingBlockEditorUiState

    data class Loaded(val trainingBlock: TrainingBlock) : TrainingBlockEditorUiState
}
