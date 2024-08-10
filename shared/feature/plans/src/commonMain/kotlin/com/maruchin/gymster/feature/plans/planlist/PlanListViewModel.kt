package com.maruchin.gymster.feature.plans.planlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.plans.repository.PlansRepository
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.get

class PlanListViewModel internal constructor(
    private val plansRepository: PlansRepository,
    private val trainingsRepository: TrainingsRepository
) : ViewModel() {

    val uiState: StateFlow<PlanListUiState> = plansRepository.observeAllPlans()
        .map { it.toPlanListUiState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = PlanListUiState.Loading
        )

    fun startNewTrainingBlock(planId: String) = viewModelScope.launch {
        val plan = plansRepository.observePlan(planId).first() ?: return@launch
        trainingsRepository.createTrainingBlock(plan)
    }

    companion object {

        fun get(): PlanListViewModel = SharedLibraryKoin.get()
    }
}
