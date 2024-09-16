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
import org.koin.core.component.get
import org.koin.core.component.inject

class TrainingEditorViewModel(
    private val trainingBlockId: String,
    private val trainingId: String,
    private val exerciseId: String
) : ViewModel(),
    KoinComponent {

    private val trainingsRepository: TrainingsRepository by inject()

    val uiState: StateFlow<TrainingEditorUiState> = trainingsRepository
        .observeTrainingBlock(trainingBlockId)
        .filterNotNull()
        .map { TrainingEditorUiState.from(it, trainingId, exerciseId) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TrainingEditorUiState.Loading
        )

    fun updateWeight(setResultId: String, weight: Double?) = viewModelScope.launch {
        trainingsRepository.updateSetResultWeight(trainingBlockId, setResultId, weight)
    }

    fun updateReps(setResultId: String, reps: Int?) = viewModelScope.launch {
        trainingsRepository.updateSetResultReps(trainingBlockId, setResultId, reps)
    }
}
