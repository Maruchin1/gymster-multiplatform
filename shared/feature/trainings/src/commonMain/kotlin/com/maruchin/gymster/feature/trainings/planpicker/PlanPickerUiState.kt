package com.maruchin.gymster.feature.trainings.planpicker

import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.PlannedTraining
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant

sealed interface PlanPickerUiState {

    data object Loading : PlanPickerUiState

    data class Loaded(
        val plans: List<Plan>,
        val selectedPlan: Plan?,
        val selectedDay: PlannedTraining?,
        val selectedDate: LocalDate
    ) : PlanPickerUiState {

        val days: List<PlannedTraining>
            get() = selectedPlan?.days.orEmpty()

        val canStartTraining: Boolean
            get() = selectedPlan != null && selectedDay != null

        val selectedDateMillis: Long
            get() = selectedDate
                .atTime(hour = 12, minute = 0)
                .toInstant(TimeZone.currentSystemDefault())
                .toEpochMilliseconds()
    }
}
