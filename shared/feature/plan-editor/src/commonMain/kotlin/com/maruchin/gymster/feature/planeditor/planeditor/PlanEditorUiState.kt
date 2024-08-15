package com.maruchin.gymster.feature.planeditor.planeditor

import com.maruchin.gymster.data.plans.model.Plan

sealed interface PlanEditorUiState {

    data object Loading : PlanEditorUiState

    data class Loaded(val plan: Plan) : PlanEditorUiState
}
