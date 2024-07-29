package com.maruchin.gymster.feature.plans.planlist

import com.maruchin.gymster.data.plans.model.Plan

sealed interface PlanListUiState {

    data object Loading : PlanListUiState

    data object Empty : PlanListUiState

    data class Loaded(val plans: List<Plan>) : PlanListUiState
}

internal fun Collection<Plan>.toPlanListUiState() = when {
    isEmpty() -> PlanListUiState.Empty
    else -> PlanListUiState.Loaded(this.toList())
}
