package com.maruchin.gymster.feature.trainings.trainingeditor

import com.maruchin.gymster.data.trainings.model.Training
import com.maruchin.gymster.data.trainings.model.TrainingBlock

data class TrainingEditorUiState(
    val training: Training? = null,
    val previousTraining: Training? = null,
    val initialExerciseIndex: Int = -1
) {

    companion object {

        fun from(
            trainingBlock: TrainingBlock,
            weekIndex: Int,
            trainingIndex: Int,
            exerciseIndex: Int
        ) = TrainingEditorUiState(
            training = trainingBlock.getTraining(weekIndex, trainingIndex),
            previousTraining = trainingBlock.getPreviousTraining(weekIndex, trainingIndex),
            initialExerciseIndex = exerciseIndex
        )
    }
}
