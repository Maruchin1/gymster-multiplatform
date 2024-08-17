package com.maruchin.gymster.feature.trainingblockdetails.progressform

import com.maruchin.gymster.data.trainings.model.Exercise
import com.maruchin.gymster.data.trainings.model.SetProgress
import com.maruchin.gymster.data.trainings.model.TrainingBlock

sealed interface ProgressFormUiState {

    data object Loading : ProgressFormUiState

    data class Loaded(val exercise: Exercise, val setProgress: SetProgress?) : ProgressFormUiState

    companion object {

        fun from(trainingBlock: TrainingBlock, setProgressId: String): Loaded = Loaded(
            exercise = trainingBlock.getExerciseForSetProgress(setProgressId),
            setProgress = trainingBlock.getSetProgress(setProgressId)
        )
    }
}
