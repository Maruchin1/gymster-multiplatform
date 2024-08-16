package com.maruchin.gymster.feature.trainingblockdetails.progressform

import com.maruchin.gymster.data.trainings.model.Exercise
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.model.TrainingBlock

sealed interface ProgressFormUiState {

    data object Loading : ProgressFormUiState

    data class Loaded(val exercise: Exercise, val progress: Progress?) : ProgressFormUiState

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
