package com.maruchin.gymster.feature.trainings.traininghistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.get

class TrainingHistoryViewModel internal constructor(
    private val trainingsRepository: TrainingsRepository
) : ViewModel() {

    val uiState: StateFlow<TrainingHistoryUiState> = trainingsRepository.observeAllTrainings()
        .map { trainings ->
            trainings.sortedByDescending { it.date }
        }
        .map { trainings ->
            if (trainings.isEmpty()) {
                TrainingHistoryUiState.Empty
            } else {
                TrainingHistoryUiState.Loaded(trainings)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = TrainingHistoryUiState.Loading
        )

    companion object {

        fun get(): TrainingHistoryViewModel = SharedLibraryKoin.get()
    }
}
