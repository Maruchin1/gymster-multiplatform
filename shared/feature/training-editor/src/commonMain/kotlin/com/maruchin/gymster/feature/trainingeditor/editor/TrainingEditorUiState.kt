package com.maruchin.gymster.feature.trainingeditor.editor

import com.maruchin.gymster.data.trainings.model.Training
import com.maruchin.gymster.data.trainings.model.TrainingBlock

sealed interface TrainingEditorUiState {

    data object Loading : TrainingEditorUiState

    data class Loaded(val training: Training) : TrainingEditorUiState

    companion object {

        fun from(trainingBlock: TrainingBlock, trainingId: String) = Loaded(
            training = trainingBlock.getTraining(trainingId)
        )
    }
}
