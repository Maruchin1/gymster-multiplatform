package com.maruchin.gymster.feature.trainings.progresseditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class ProgressEditorViewModel internal constructor(
    private val trainingBlockId: String,
    private val weekNumber: Int,
    private val trainingId: String,
    private val exerciseId: String,
    private val progressIndex: Int,
    private val trainingsRepository: TrainingsRepository
) : ViewModel() {

    val uiState: StateFlow<ProgressEditorUiState> = trainingsRepository
        .observeTrainingBlock(trainingBlockId)
        .filterNotNull()
        .map { ProgressEditorUiState.from(it, weekNumber, trainingId, exerciseId, progressIndex) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProgressEditorUiState.Loading
        )

    fun saveProgress(progress: Progress) = viewModelScope.launch {
        trainingsRepository.updateProgress(
            trainingBlockId = trainingBlockId,
            weekNumber = weekNumber,
            trainingId = trainingId,
            exerciseId = exerciseId,
            progressIndex = progressIndex,
            newProgress = progress
        )
    }

    companion object {

        fun get(
            trainingBlockId: String,
            weekNumber: Int,
            trainingId: String,
            exerciseId: String,
            progressIndex: Int
        ): ProgressEditorViewModel = SharedLibraryKoin.get {
            parametersOf(trainingBlockId, weekNumber, trainingId, exerciseId, progressIndex)
        }
    }
}
