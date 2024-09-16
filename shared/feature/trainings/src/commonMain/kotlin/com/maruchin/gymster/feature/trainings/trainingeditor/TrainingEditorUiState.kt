package com.maruchin.gymster.feature.trainings.trainingeditor

import com.maruchin.gymster.data.trainings.model.Training
import com.maruchin.gymster.data.trainings.model.TrainingBlock

data class TrainingEditorUiState(
    val training: Training? = null,
    val initialExerciseId: String = ""
) {

    companion object {

        fun from(trainingBlock: TrainingBlock, trainingId: String, exerciseId: String) =
            TrainingEditorUiState(
                training = trainingBlock.getTraining(trainingId),
                initialExerciseId = exerciseId
            )
    }
}
