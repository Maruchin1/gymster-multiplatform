package com.maruchin.gymster.feature.trainings.starttrainingblock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.repository.PlansRepository2
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StartTrainingBlockViewModel :
    ViewModel(),
    KoinComponent {

    private val plansRepository: PlansRepository2 by inject()
    private val trainingsRepository: TrainingsRepository by inject()

    private val isCreated = MutableStateFlow(false)
    private val selectedPlan = MutableStateFlow<Plan?>(null)
    private val selectedStartDate = MutableStateFlow<LocalDate?>(null)
    private val selectedWeeksDuration = MutableStateFlow<Int?>(null)

    val uiState: StateFlow<StartTrainingBlockUiState> = combine(
        plansRepository.observeAllPlans(),
        selectedPlan,
        selectedStartDate,
        selectedWeeksDuration,
        isCreated,
        ::StartTrainingBlockUiState
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = StartTrainingBlockUiState()
    )

    fun selectPlan(plan: Plan) {
        selectedPlan.value = plan
    }

    fun resetPlan() {
        selectedPlan.value = null
    }

    fun selectStarDate(date: LocalDate) {
        selectedStartDate.value = date
    }

    fun resetStartDate() {
        selectedStartDate.value = null
    }

    fun selectWeeksDuration(duration: Int) {
        selectedWeeksDuration.value = duration
    }

    fun resetWeeksDuration() {
        selectedWeeksDuration.value = null
    }

    fun startTrainingBlock() = viewModelScope.launch {
        val plan = checkNotNull(selectedPlan.value)
        val startDate = checkNotNull(selectedStartDate.value)
        val weeksDuration = checkNotNull(selectedWeeksDuration.value)
        val trainingBlock = trainingsRepository.createTrainingBlock(plan, startDate, weeksDuration)
        trainingsRepository.setActiveTrainingBlock(trainingBlock.id)
        isCreated.value = true
    }
}
