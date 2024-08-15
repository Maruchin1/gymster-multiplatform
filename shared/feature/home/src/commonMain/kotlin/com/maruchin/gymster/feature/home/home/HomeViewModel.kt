package com.maruchin.gymster.feature.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.get

class HomeViewModel(trainingsRepository: TrainingsRepository) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = trainingsRepository.observeAllTrainingBlocks()
        .map { HomeUiState.from(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = HomeUiState.Loading
        )

    companion object {

        fun get(): HomeViewModel = SharedLibraryKoin.get()
    }
}
