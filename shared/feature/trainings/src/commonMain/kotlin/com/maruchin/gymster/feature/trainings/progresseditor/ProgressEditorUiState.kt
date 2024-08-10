package com.maruchin.gymster.feature.trainings.progresseditor

import com.maruchin.gymster.data.trainings.model.Exercise
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.TrainingBlock

sealed interface ProgressEditorUiState {

    data object Loading : ProgressEditorUiState

    data class Loaded(val exercise: Exercise, val progress: Progress) : ProgressEditorUiState

    companion object {

        fun from(
            trainingBlock: TrainingBlock,
            weekNumber: Int,
            trainingId: String,
            exerciseId: String,
            progressIndex: Int
        ): Loaded {
            val week = trainingBlock.getWeek(weekNumber)
            val training = week.getTraining(trainingId)
            val exercise = training.getExercise(exerciseId)
            val progress = exercise.getProgress(progressIndex)
            return Loaded(exercise, progress)
        }
    }
}
