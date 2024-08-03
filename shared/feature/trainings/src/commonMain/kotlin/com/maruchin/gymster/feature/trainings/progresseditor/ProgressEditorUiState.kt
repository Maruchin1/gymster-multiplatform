package com.maruchin.gymster.feature.trainings.progresseditor

import com.maruchin.gymster.data.trainings.model.Progress

sealed interface ProgressEditorUiState {

    data object Loading : ProgressEditorUiState

    data class Loaded(val progress: Progress) : ProgressEditorUiState
}
