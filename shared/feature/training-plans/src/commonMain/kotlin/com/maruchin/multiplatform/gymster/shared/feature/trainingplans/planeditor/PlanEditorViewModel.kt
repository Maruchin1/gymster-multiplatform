package com.maruchin.multiplatform.gymster.shared.feature.trainingplans.planeditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.multiplatform.gymster.shared.core.di.SharedLibraryKoin
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.repository.TrainingPlansRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class PlanEditorViewModel internal constructor(
    private val planId: String,
    private val trainingPlansRepository: TrainingPlansRepository
) : ViewModel() {

    val uiState: StateFlow<PlanEditorUiState> = trainingPlansRepository.observePlan(planId)
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
        trainingPlansRepository.deletePlan(planId = planId)
    }

    fun deleteDay(dayId: String) = viewModelScope.launch {
        trainingPlansRepository.deleteDay(planId = planId, dayId = dayId)
    }

    fun deleteExercise(dayId: String, exerciseId: String) = viewModelScope.launch {
        trainingPlansRepository.deleteExercise(
            planId = planId,
            dayId = dayId,
            exerciseId = exerciseId
        )
    }

    fun reorderExercises(dayId: String, exercisesIds: List<String>) = viewModelScope.launch {
        trainingPlansRepository.reorderExercises(
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
