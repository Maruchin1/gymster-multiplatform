package com.maruchin.gymster.feature.plans.planeditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import com.maruchin.gymster.data.plans.repository.PlansRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class PlanEditorViewModel internal constructor(
    private val planId: String,
    private val plansRepository: PlansRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PlanEditorUiState>(PlanEditorUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        collectPlan()
    }

    fun changePlanName(newName: String) = viewModelScope.launch {
        plansRepository.changePlanName(planId = planId, newName = newName)
    }

    fun deletePlan() = viewModelScope.launch {
        plansRepository.deletePlan(planId = planId)
        _uiState.value = PlanEditorUiState.Deleted
    }

    fun addTraining(name: String) = viewModelScope.launch {
        plansRepository.addTraining(planId = planId, name = name)
    }

    fun updateTraining(trainingId: String, newName: String) = viewModelScope.launch {
        plansRepository.changeTrainingName(
            planId = planId,
            trainingId = trainingId,
            newName = newName
        )
    }

    fun deleteTraining(trainingId: String) = viewModelScope.launch {
        plansRepository.deleteTraining(planId = planId, trainingId = trainingId)
    }

    fun addExercise(trainingId: String, name: String, sets: Sets, reps: Reps) =
        viewModelScope.launch {
            plansRepository.addExercise(
                planId = planId,
                trainingId = trainingId,
                name = name,
                sets = sets,
                reps = reps
            )
        }

    fun updateExercise(exerciseId: String, name: String, sets: Sets, reps: Reps) =
        viewModelScope.launch {
            plansRepository.updateExercise(
                planId = planId,
                exerciseId = exerciseId,
                newName = name,
                newSets = sets,
                newReps = reps
            )
        }

    fun deleteExercise(exerciseId: String) = viewModelScope.launch {
        plansRepository.deleteExercise(planId = planId, exerciseId = exerciseId)
    }

    fun reorderExercises(trainingId: String, exercisesIds: List<String>) = viewModelScope.launch {
        plansRepository.reorderExercises(
            planId = planId,
            trainingId = trainingId,
            exercisesIds = exercisesIds
        )
    }

    private fun collectPlan() = viewModelScope.launch {
        plansRepository.observePlan(planId)
            .filterNotNull()
            .map { PlanEditorUiState.Loaded(it) }
            .collectLatest { _uiState.value = it }
    }

    companion object : KoinComponent {

        fun create(planId: String): PlanEditorViewModel = get { parametersOf(planId) }
    }
}