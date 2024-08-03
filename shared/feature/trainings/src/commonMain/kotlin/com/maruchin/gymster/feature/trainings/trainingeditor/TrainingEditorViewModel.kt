package com.maruchin.gymster.feature.trainings.trainingeditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class TrainingEditorViewModel internal constructor(
    private val trainingId: String,
    private val trainingsRepository: TrainingsRepository
) : ViewModel() {

    val uiState: StateFlow<TrainingEditorUiState> = trainingsRepository.observeTraining(trainingId)
        .filterNotNull()
        .map { TrainingEditorUiState.Loaded(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TrainingEditorUiState.Loading
        )

    fun deleteTraining() = viewModelScope.launch {
        trainingsRepository.deleteTraining(trainingId)
    }

    companion object {

        fun get(trainingId: String): TrainingEditorViewModel =
            SharedLibraryKoin.get { parametersOf(trainingId) }
    }
}
