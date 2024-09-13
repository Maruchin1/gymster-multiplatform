package com.maruchin.gymster.feature.trainings.starttrainingblock

import com.maruchin.gymster.data.plans.model.Plan
import kotlinx.datetime.LocalDate

data class StartTrainingBlockUiState(
    val plans: List<Plan> = emptyList(),
    val selectedPlan: Plan? = null,
    val selectedStartDate: LocalDate? = null,
    val selectedWeeksDuration: Int? = null,
    val isCreated: Boolean = false
) {

    val canCreate: Boolean
        get() = selectedPlan != null &&
            selectedStartDate != null &&
            selectedWeeksDuration != null
}
