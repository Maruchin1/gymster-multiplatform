package com.maruchin.gymster.android.planeditor.planeditor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.maruchin.gymster.android.planeditor.PlanEditorGraph
import com.maruchin.gymster.feature.planeditor.planeditor.PlanEditorViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object PlanEditorRoute

internal fun NavGraphBuilder.planEditorScreen(navController: NavController, onBack: () -> Unit) {
    composable<PlanEditorRoute> { entry ->
        val (planId) = remember(navController, entry) {
            navController.getBackStackEntry<PlanEditorGraph>().toRoute<PlanEditorGraph>()
        }
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
