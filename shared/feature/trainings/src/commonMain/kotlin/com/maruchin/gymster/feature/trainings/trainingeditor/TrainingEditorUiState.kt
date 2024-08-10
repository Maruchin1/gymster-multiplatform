package com.maruchin.gymster.feature.trainings.trainingeditor

import com.maruchin.gymster.data.trainings.model.Training
import com.maruchin.gymster.data.trainings.model.TrainingBlock

sealed interface TrainingEditorUiState {

    data object Loading : TrainingEditorUiState

    data class Loaded(val training: Training) : TrainingEditorUiState

    companion object {

        fun from(trainingBlock: TrainingBlock, weekNumber: Int, trainingId: String): Loaded {
            val week = trainingBlock.getWeek(weekNumber)
            val training = week.getTraining(trainingId)
            return Loaded(training)
        }
    }
}
