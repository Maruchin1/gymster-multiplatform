package com.maruchin.gymster.feature.trainings.trainingeditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TrainingEditorViewModel(
    private val trainingBlockId: String,
    private val weekIndex: Int,
    private val trainingIndex: Int,
    private val initialExerciseIndex: Int
) : ViewModel(),
    KoinComponent {

    private val trainingsRepository: TrainingsRepository by inject()

    val uiState: StateFlow<TrainingEditorUiState> = trainingsRepository
        .observeTrainingBlock(trainingBlockId)
        .filterNotNull()
        .map { TrainingEditorUiState.from(it, weekIndex, trainingIndex, initialExerciseIndex) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TrainingEditorUiState()
        )

    fun updateWeight(exerciseIndex: Int, setIndex: Int, weight: Double?) = viewModelScope.launch {
        trainingsRepository.updateSetResultWeight(
            trainingBlockId = trainingBlockId,
            weekIndex = weekIndex,
            trainingIndex = trainingIndex,
            exerciseIndex = exerciseIndex,
            setIndex = setIndex,
            weight = weight
        )
    }

    fun updateReps(exerciseIndex: Int, setIndex: Int, reps: Int?) = viewModelScope.launch {
        trainingsRepository.updateSetResultReps(
            trainingBlockId = trainingBlockId,
            weekIndex = weekIndex,
            trainingIndex = trainingIndex,
            exerciseIndex = exerciseIndex,
            setIndex = setIndex,
            reps = reps
        )
    }
}
