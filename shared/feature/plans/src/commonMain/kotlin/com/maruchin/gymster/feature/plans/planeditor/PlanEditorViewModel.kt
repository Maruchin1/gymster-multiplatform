package com.maruchin.gymster.feature.plans.planeditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import com.maruchin.gymster.data.plans.repository.PlansRepository2
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlanEditorViewModel(private val planId: String) :
    ViewModel(),
    KoinComponent {

    private val plansRepository: PlansRepository2 by inject()

    private val isDeleted = MutableStateFlow(false)

    val uiState: StateFlow<PlanEditorUiState> = combine(
        plansRepository.observePlan(planId),
        isDeleted,
        ::PlanEditorUiState
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PlanEditorUiState()
    )

    fun changePlanName(name: String) = viewModelScope.launch {
        plansRepository.updatePlan(planId = planId, name = name)
    }

    fun deletePlan() = viewModelScope.launch {
        plansRepository.deletePlan(planId = planId)
        isDeleted.value = true
    }

    fun addTraining(name: String) = viewModelScope.launch {
        plansRepository.addTraining(planId = planId, name = name)
    }

    fun updateTraining(trainingId: String, name: String) = viewModelScope.launch {
        plansRepository.updateTraining(trainingId = trainingId, name = name)
    }

    fun deleteTraining(trainingId: String) = viewModelScope.launch {
        plansRepository.deleteTraining(trainingId = trainingId)
    }

    fun addExercise(trainingId: String, name: String, sets: Sets, reps: Reps) =
        viewModelScope.launch {
            plansRepository.addExercise(
                trainingId = trainingId,
                name = name,
                sets = sets,
                reps = reps
            )
        }

    fun updateExercise(exerciseId: String, name: String, sets: Sets, reps: Reps) =
        viewModelScope.launch {
            plansRepository.updateExercise(
                exerciseId = exerciseId,
                name = name,
                sets = sets,
                reps = reps
            )
        }

    fun deleteExercise(exerciseId: String) = viewModelScope.launch {
        plansRepository.deleteExercise(exerciseId)
    }

    fun reorderExercises(exercisesIds: List<String>) = viewModelScope.launch {
        plansRepository.reorderExercises(exercisesIds)
    }
}
