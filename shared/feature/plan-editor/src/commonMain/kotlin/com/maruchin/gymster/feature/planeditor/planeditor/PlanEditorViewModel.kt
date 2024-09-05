package com.maruchin.gymster.feature.planeditor.planeditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.plans.repository.PlansRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
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
        .filterNotNull()
        .map { PlanEditorUiState.Loaded(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = PlanEditorUiState.Loading
        )

    fun changePlanName(newName: String) = viewModelScope.launch {
        plansRepository.changePlanName(planId = planId, newName = newName)
    }

    fun deletePlan() = viewModelScope.launch {
        plansRepository.deletePlan(planId = planId)
    }

    fun deleteTraining(dayId: String) = viewModelScope.launch {
        plansRepository.deleteTraining(planId = planId, trainingId = dayId)
    }

    fun deleteExercise(dayId: String, exerciseId: String) = viewModelScope.launch {
        plansRepository.deleteExercise(
            planId = planId,
            trainingId = dayId,
            exerciseId = exerciseId
        )
    }

    fun reorderExercises(dayId: String, exercisesIds: List<String>) = viewModelScope.launch {
        plansRepository.reorderExercises(
            planId = planId,
            trainingId = dayId,
            exercisesIds = exercisesIds
        )
    }

    companion object {

        fun get(planId: String): PlanEditorViewModel =
            SharedLibraryKoin.get { parametersOf(planId) }
    }
}
