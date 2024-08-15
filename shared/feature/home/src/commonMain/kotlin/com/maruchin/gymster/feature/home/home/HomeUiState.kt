package com.maruchin.gymster.feature.home.home

import com.maruchin.gymster.data.trainings.model.TrainingBlock

sealed interface HomeUiState {

    data object Loading : HomeUiState

    data object Empty : HomeUiState

    data class Loaded(val trainingBlocks: List<TrainingBlock>) : HomeUiState

    companion object {

        fun from(trainingBlocks: List<TrainingBlock>) = when {
            trainingBlocks.isEmpty() -> Empty
            else -> Loaded(trainingBlocks)
        }
    }
}
