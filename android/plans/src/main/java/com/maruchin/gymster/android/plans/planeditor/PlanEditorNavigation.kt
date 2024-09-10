package com.maruchin.gymster.android.plans.planeditor

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.maruchin.gymster.feature.plans.planeditor.PlanEditorViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data class PlanEditorRoute(val planId: String)

internal fun NavGraphBuilder.planEditorScreen(onBack: () -> Unit) {
    composable<PlanEditorRoute> { entry ->
        val (planId) = entry.toRoute<PlanEditorRoute>()
        val viewModel = viewModel { PlanEditorViewModel.create(planId) }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        PlanEditorScreen(
            state = state,
            onBack = onBack,
            onUpdatePlan = { viewModel.changePlanName(it) },
            onDeletePlan = { viewModel.deletePlan().invokeOnCompletion { onBack() } },
            onAddTraining = viewModel::addTraining,
            onUpdateTraining = viewModel::updateTraining,
            onDeleteTraining = viewModel::deleteTraining,
            onAddExercise = viewModel::addExercise,
            onUpdateExercise = viewModel::updateExercise,
            onDeleteExercise = viewModel::deleteExercise,
            onReorderExercises = viewModel::reorderExercises
        )
    }
}
