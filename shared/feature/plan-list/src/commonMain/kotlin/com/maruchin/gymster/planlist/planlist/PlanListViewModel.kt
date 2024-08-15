package com.maruchin.gymster.planlist.planlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.plans.repository.PlansRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.get

class PlanListViewModel internal constructor(plansRepository: PlansRepository) : ViewModel() {

    val uiState: StateFlow<PlanListUiState> = plansRepository.observeAllPlans()
        .map { PlanListUiState.from(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = PlanListUiState.Loading
        )

    companion object {

        fun get(): PlanListViewModel = SharedLibraryKoin.get()
    }
}
