package com.maruchin.multiplatform.gymster.feature.trainingplans.planlist

import com.maruchin.multiplatform.gymster.data.trainingplans.model.TrainingPlan

sealed interface PlanListUiState {

    data object Loading : PlanListUiState

    data object Empty : PlanListUiState

    data class Loaded(val plans: List<TrainingPlan>) : PlanListUiState
}

internal fun Collection<TrainingPlan>.toPlanListUiState() = when {
    isEmpty() -> PlanListUiState.Empty
    else -> PlanListUiState.Loaded(this.toList())
}
