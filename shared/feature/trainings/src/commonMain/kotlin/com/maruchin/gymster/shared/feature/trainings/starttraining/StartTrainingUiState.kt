package com.maruchin.gymster.shared.feature.trainings.starttraining

import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.PlannedTraining
import kotlinx.datetime.LocalDate

data class StartTrainingUiState(
    val plans: List<Plan> = emptyList(),
    val selectedPlan: Plan? = null,
    val selectedTraining: PlannedTraining? = null,
    val selectedDate: LocalDate? = null,
    val isCreated: Boolean = false
) {

    val trainings: List<PlannedTraining>
        get() = selectedPlan?.trainings.orEmpty()

    val canCreate: Boolean
        get() = selectedPlan != null &&
            selectedTraining != null &&
            selectedDate != null
}
