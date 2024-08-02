package com.maruchin.gymster.feature.trainings.planpicker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.PlanDay
import com.maruchin.gymster.data.plans.repository.PlansRepository
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import org.koin.core.component.get

class PlanPickerViewModel(
    private val plansRepository: PlansRepository,
    private val trainingsRepository: TrainingsRepository,
    private val clock: Clock
) : ViewModel() {

    private val selectedPlan = MutableStateFlow<Plan?>(null)
    private val selectedDay = MutableStateFlow<PlanDay?>(null)
    private val selectedDate = MutableStateFlow(getToday())

    val uiState: StateFlow<PlanPickerUiState> = combine(
        plansRepository.observeAllPlans(),
        selectedPlan,
        selectedDay,
        selectedDate,
        PlanPickerUiState::Loaded
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PlanPickerUiState.Loading
    )

    fun selectPlan(plan: Plan) {
        selectedPlan.value = plan
    }

    fun selectDay(planDay: PlanDay) {
        selectedDay.value = planDay
    }

    fun selectDate(millis: Long) {
        val timeZone = TimeZone.currentSystemDefault()
        val instant = Instant.fromEpochMilliseconds(millis)
        selectedDate.value = instant.toLocalDateTime(timeZone).date
    }

    fun startTraining() = viewModelScope.launch {
        val plan = selectedPlan.value ?: return@launch
        val day = selectedDay.value ?: return@launch
        val date = selectedDate.value
        trainingsRepository.createTraining(date = date, planName = plan.name, planDay = day)
    }

    private fun getToday(): LocalDate {
        val timeZone = TimeZone.currentSystemDefault()
        return clock.todayIn(timeZone)
    }

    companion object {

        fun get(): PlanPickerViewModel = SharedLibraryKoin.get()
    }
}
