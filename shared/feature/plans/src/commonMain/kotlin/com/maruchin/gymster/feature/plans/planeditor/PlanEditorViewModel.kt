package com.maruchin.gymster.feature.plans.planeditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.plans.repository.PlansRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class PlanEditorViewModel internal constructor(
    private val planId: String,
    private val plansRepository: PlansRepository
) : ViewModel() {

    val uiState: StateFlow<PlanEditorUiState> = plansRepository.observePlan(planId)
        .map { plan ->
            if (plan == null) {
                PlanEditorUiState.Error
            } else {
                PlanEditorUiState.Loaded(plan)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = PlanEditorUiState.Loading
        )

    fun deletePlan() = viewModelScope.launch {
        plansRepository.deletePlan(planId = planId)
    }

    fun deleteDay(dayId: String) = viewModelScope.launch {
        plansRepository.deleteDay(planId = planId, dayId = dayId)
    }

    fun deleteExercise(dayId: String, exerciseId: String) = viewModelScope.launch {
        plansRepository.deleteExercise(
            planId = planId,
            dayId = dayId,
            exerciseId = exerciseId
        )
    }

    fun reorderExercises(dayId: String, exercisesIds: List<String>) = viewModelScope.launch {
        plansRepository.reorderExercises(
            planId = planId,
            dayId = dayId,
            exercisesIds = exercisesIds
        )
    }

    companion object {

        fun get(planId: String): PlanEditorViewModel =
            SharedLibraryKoin.get { parametersOf(planId) }
    }
}
