package com.maruchin.gymster.shared.feature.trainings.trainingeditor

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

class TrainingEditorViewModel(trainingId: String) :
    ViewModel(),
    KoinComponent {

    private val trainingsRepository: TrainingsRepository by inject()

    val uiState: StateFlow<TrainingEditorUiState> = trainingsRepository
        .observeTraining(trainingId)
        .filterNotNull()
        .map { TrainingEditorUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TrainingEditorUiState()
        )

    fun updateWeight(setResultId: String, weight: Double?) = viewModelScope.launch {
        trainingsRepository.updateSetResultWeight(setResultId, weight)
    }

    fun updateReps(setResultId: String, reps: Int?) = viewModelScope.launch {
        trainingsRepository.updateSetResultReps(setResultId, reps)
    }
}
