package com.maruchin.gymster.shared.feature.trainings.starttraining

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.utils.currentDate
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.PlannedTraining
import com.maruchin.gymster.data.plans.repository.PlansRepository
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StartTrainingViewModel :
    ViewModel(),
    KoinComponent {

    private val plansRepository: PlansRepository by inject()
    private val trainingsRepository: TrainingsRepository by inject()
    private val clock: Clock by inject()

    private val _uiState = MutableStateFlow(StartTrainingUiState(selectedDate = clock.currentDate))
    val uiState = _uiState.asStateFlow()

    init {
        loadPlans()
    }

    fun selectPlan(plan: Plan) {
        _uiState.update { it.copy(selectedPlan = plan) }
    }

    fun resetPlan() {
        _uiState.update { it.copy(selectedPlan = null) }
    }

    fun selectTraining(training: PlannedTraining) {
        _uiState.update { it.copy(selectedTraining = training) }
    }

    fun resetTraining() {
        _uiState.update { it.copy(selectedTraining = null) }
    }

    fun selectDate(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
    }

    fun resetDate() {
        _uiState.update { it.copy(selectedDate = null) }
    }

    fun startTraining() = viewModelScope.launch {
        val currentState = _uiState.value
        val plan = checkNotNull(currentState.selectedPlan)
        val training = checkNotNull(currentState.selectedTraining)
        val date = checkNotNull(currentState.selectedDate)
        trainingsRepository.addTraining(plan, training, date)
        _uiState.update { it.copy(isCreated = true) }
    }

    private fun loadPlans() = viewModelScope.launch {
        val plans = plansRepository.observeAllPlans().first()
        _uiState.update { it.copy(plans = plans) }
    }
}
