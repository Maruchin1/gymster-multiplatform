package com.maruchin.gymster.feature.trainingblockdetails.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class TimelineViewModel internal constructor(
    private val trainingBlockId: String,
    private val trainingsRepository: TrainingsRepository
) : ViewModel() {

    val uiState: StateFlow<TimelineUiState> = trainingsRepository
        .observeTrainingBlock(trainingBlockId)
        .filterNotNull()
        .map { TimelineUiState.Loaded(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = TimelineUiState.Loading
        )

    companion object {

        fun get(trainingBlockId: String): TimelineViewModel =
            SharedLibraryKoin.get { parametersOf(trainingBlockId) }
    }
}
