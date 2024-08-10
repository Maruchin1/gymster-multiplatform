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
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class TrainingEditorViewModel internal constructor(
    private val trainingBlockId: String,
    private val weekNumber: Int,
    private val trainingId: String,
    private val trainingsRepository: TrainingsRepository
) : ViewModel() {

    val uiState: StateFlow<TrainingEditorUiState> = trainingsRepository
        .observeTrainingBlock(trainingBlockId)
        .filterNotNull()
        .map { TrainingEditorUiState.from(it, weekNumber, trainingId) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TrainingEditorUiState.Loading
        )

    companion object {

        fun get(
            trainingBlockId: String,
            weekNumber: Int,
            trainingId: String
        ): TrainingEditorViewModel =
            SharedLibraryKoin.get { parametersOf(trainingBlockId, weekNumber, trainingId) }
    }
}
