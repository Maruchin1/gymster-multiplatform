package com.maruchin.gymster.planlist.planlist

import com.maruchin.gymster.data.plans.model.Plan

sealed interface PlanListUiState {

    data object Loading : PlanListUiState

    data object Empty : PlanListUiState

    data class Loaded(val plans: List<Plan>) : PlanListUiState

    companion object {

        fun from(plans: List<Plan>) = when {
            plans.isEmpty() -> Empty
            else -> Loaded(plans)
        }
    }
}
