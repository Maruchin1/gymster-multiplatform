package com.maruchin.gymster.feature.trainingblockdetails.timeline

import com.maruchin.gymster.data.trainings.model.TrainingBlock

sealed interface TimelineUiState {

    data object Loading : TimelineUiState

    data class Loaded(val trainingBlock: TrainingBlock) : TimelineUiState
}
