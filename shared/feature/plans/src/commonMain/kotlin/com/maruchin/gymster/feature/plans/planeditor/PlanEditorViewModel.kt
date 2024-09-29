package com.maruchin.gymster.feature.plans.planeditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import com.maruchin.gymster.data.plans.repository.PlansRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject

class PlanEditorViewModel(private val planId: String) :
    ViewModel(),
    KoinComponent {

    private val plansRepository: PlansRepository by inject()

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

    fun changePlanName(newName: String) = viewModelScope.launch {
        plansRepository.changePlanName(planId = planId, newName = newName)
    }

    fun deletePlan() = viewModelScope.launch {
        plansRepository.deletePlan(planId = planId)
        isDeleted.value = true
    }

    fun addTraining(name: String) = viewModelScope.launch {
        plansRepository.addTraining(planId = planId, name = name)
    }

    fun updateTraining(trainingIndex: Int, newName: String) = viewModelScope.launch {
        plansRepository.changeTrainingName(
            planId = planId,
            trainingIndex = trainingIndex,
            newName = newName
        )
    }

    fun deleteTraining(trainingIndex: Int) = viewModelScope.launch {
        plansRepository.deleteTraining(planId = planId, trainingIndex = trainingIndex)
    }

    fun addExercise(trainingIndex: Int, name: String, sets: Sets, reps: Reps) =
        viewModelScope.launch {
            plansRepository.addExercise(
                planId = planId,
                trainingIndex = trainingIndex,
                name = name,
                sets = sets,
                reps = reps
            )
        }

    fun updateExercise(
        trainingIndex: Int,
        exerciseIndex: Int,
        name: String,
        sets: Sets,
        reps: Reps
    ) = viewModelScope.launch {
        plansRepository.updateExercise(
            planId = planId,
            trainingIndex = trainingIndex,
            exerciseIndex = exerciseIndex,
            newName = name,
            newSets = sets,
            newReps = reps
        )
    }

    fun deleteExercise(trainingIndex: Int, exerciseIndex: Int) = viewModelScope.launch {
        plansRepository.deleteExercise(
            planId = planId,
            trainingIndex = trainingIndex,
            exerciseIndex = exerciseIndex
        )
    }

    fun reorderExercises(trainingIndex: Int, exercisesIds: List<String>) = viewModelScope.launch {
        plansRepository.reorderExercises(
            planId = planId,
            trainingIndex = trainingIndex,
            exercisesIds = exercisesIds
        )
    }
}
