package com.maruchin.gymster.feature.trainings.progresseditor

import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.Training
import com.maruchin.gymster.data.trainings.model.TrainingExercise

sealed interface ProgressEditorUiState {

    data object Loading : ProgressEditorUiState

    data class Loaded(val exercise: TrainingExercise, val progress: Progress) :
        ProgressEditorUiState {

        constructor(training: Training, exerciseId: String, progressIndex: Int) : this(
            exercise = training.getExercise(exerciseId),
            progress = training.getExercise(exerciseId).getProgress(progressIndex)
        )
    }
}
