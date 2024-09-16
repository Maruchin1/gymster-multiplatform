package com.maruchin.gymster.feature.trainings.trainingeditor

import com.maruchin.gymster.data.trainings.model.Training
import com.maruchin.gymster.data.trainings.model.TrainingBlock

sealed interface TrainingEditorUiState {

    data object Loading : TrainingEditorUiState

    data class Loaded(val training: Training, val initialExerciseId: String) : TrainingEditorUiState

    companion object {

        fun from(trainingBlock: TrainingBlock, trainingId: String, exerciseId: String) = Loaded(
            training = trainingBlock.getTraining(trainingId),
            initialExerciseId = exerciseId
        )
    }
}
