package com.maruchin.gymster.feature.trainings.trainingblockeditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class TrainingBlockEditorViewModel internal constructor(
    private val trainingBlockId: String,
    private val trainingsRepository: TrainingsRepository
) : ViewModel() {

    val uiState: StateFlow<TrainingBlockEditorUiState> = trainingsRepository
        .observeTrainingBlock(trainingBlockId)
        .filterNotNull()
        .map { TrainingBlockEditorUiState.Loaded(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = TrainingBlockEditorUiState.Loading
        )

    companion object : KoinComponent {

        fun create(trainingBlockId: String): TrainingBlockEditorViewModel = get {
            parametersOf(trainingBlockId)
        }
    }
}
