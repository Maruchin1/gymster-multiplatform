package com.maruchin.multiplatform.gymster.feature.trainingplans.planlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.multiplatform.gymster.core.di.SharedLibraryKoin
import com.maruchin.multiplatform.gymster.data.trainingplans.repository.TrainingPlansRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.get

class PlanListViewModel internal constructor(trainingPlansRepository: TrainingPlansRepository) :
    ViewModel() {

    val uiState: StateFlow<PlanListUiState> = trainingPlansRepository.observeAllPlans()
        .map { it.toPlanListUiState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = PlanListUiState.Loading
        )

    companion object {

        fun get(): PlanListViewModel = SharedLibraryKoin.get()
    }
}
