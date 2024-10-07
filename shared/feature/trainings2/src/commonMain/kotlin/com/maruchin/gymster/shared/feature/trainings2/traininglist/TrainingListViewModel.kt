package com.maruchin.gymster.shared.feature.trainings2.traininglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.data.trainings2.repository.TrainingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TrainingListViewModel :
    ViewModel(),
    KoinComponent {

    private val trainingsRepository: TrainingsRepository by inject()

    val uiState: StateFlow<TrainingListUiState> = trainingsRepository.observeAllTrainings()
        .map { TrainingListUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = TrainingListUiState()
        )
}
