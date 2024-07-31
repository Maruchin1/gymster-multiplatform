package com.maruchin.gymster.feature.trainings.traininghistory

import com.maruchin.gymster.data.trainings.model.Training

sealed interface TrainingHistoryUiState {

    data object Loading : TrainingHistoryUiState

    data object Empty : TrainingHistoryUiState

    data class Loaded(val trainings: List<Training>) : TrainingHistoryUiState
}
