package com.maruchin.gymster.feature.trainings.trainingblocklist

import com.maruchin.gymster.data.trainings.model.TrainingBlock

sealed interface TrainingBlockListUiState {

    data object Loading : TrainingBlockListUiState

    data class Loaded(val trainingBlocks: List<TrainingBlock>) : TrainingBlockListUiState
}
