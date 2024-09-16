package com.maruchin.gymster.feature.plans.planeditor

import com.maruchin.gymster.data.plans.model.Plan

data class PlanEditorUiState(val plan: Plan? = null, val isDeleted: Boolean = false)
