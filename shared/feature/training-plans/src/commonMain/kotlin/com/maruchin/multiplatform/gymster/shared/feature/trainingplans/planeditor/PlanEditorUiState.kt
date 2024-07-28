package com.maruchin.multiplatform.gymster.shared.feature.trainingplans.planeditor

import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.TrainingPlan

sealed interface PlanEditorUiState {

    data object Loading : PlanEditorUiState

    data class Loaded(val plan: TrainingPlan) : PlanEditorUiState

    data object Error : PlanEditorUiState
}
