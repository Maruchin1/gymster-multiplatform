package com.maruchin.gymster.feature.trainingblockdetails.progressform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.data.trainings.model.Progress
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class ProgressFormViewModel internal constructor(
    private val trainingBlockId: String,
    private val setProgressId: String,
    private val trainingsRepository: TrainingsRepository
) : ViewModel() {

    val uiState: StateFlow<ProgressFormUiState> = trainingsRepository
        .observeTrainingBlock(trainingBlockId)
        .filterNotNull()
        .map { ProgressFormUiState.from(it, setProgressId) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProgressFormUiState.Loading
        )

    fun saveProgress(progress: Progress) = viewModelScope.launch {
        trainingsRepository.updateProgress(
            trainingBlockId = trainingBlockId,
            setProgressId = setProgressId,
            newProgress = progress
        )
    }

    companion object : KoinComponent {

        fun create(trainingBlockId: String, setProgressId: String): ProgressFormViewModel =
            get { parametersOf(trainingBlockId, setProgressId) }
    }
}
