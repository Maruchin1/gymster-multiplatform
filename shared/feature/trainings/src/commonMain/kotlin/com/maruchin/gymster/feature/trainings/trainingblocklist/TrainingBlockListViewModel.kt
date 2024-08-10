package com.maruchin.gymster.feature.trainings.trainingblocklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.get

class TrainingBlockListViewModel internal constructor(
    private val trainingsRepository: TrainingsRepository
) : ViewModel() {

    val uiState: StateFlow<TrainingBlockListUiState> = trainingsRepository
        .observeAllTrainingBlocks()
        .map { trainingBlocks ->
            if (trainingBlocks.isEmpty()) {
                TrainingBlockListUiState.Empty
            } else {
                TrainingBlockListUiState.Loaded(trainingBlocks)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = TrainingBlockListUiState.Loading
        )

    companion object {

        fun get(): TrainingBlockListViewModel = SharedLibraryKoin.get()
    }
}
