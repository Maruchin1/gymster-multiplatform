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

internal fun NavGraphBuilder.planEditorScreen(
    navController: NavController,
    onBack: () -> Unit,
    onAddTraining: (planId: String) -> Unit,
    onEditTraining: (planId: String, trainingId: String) -> Unit,
    onAddExercise: (planId: String, trainingId: String) -> Unit,
    onEditExercise: (planId: String, trainingId: String, exerciseId: String) -> Unit
) {
    composable<PlanEditorRoute> { entry ->
        val (planId) = remember(navController, entry) {
            navController.getBackStackEntry<PlanEditorGraph>().toRoute<PlanEditorGraph>()
        }
        val viewModel = viewModel { PlanEditorViewModel.get(planId) }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        PlanEditorScreen(
            state = state,
            onBack = onBack,
            onChangePlanName = { viewModel.changePlanName(it) },
            onDeletePlan = { viewModel.deletePlan().invokeOnCompletion { onBack() } },
            onAddTraining = { onAddTraining(planId) },
            onEditTraining = { trainingId -> onEditTraining(planId, trainingId) },
            onDeleteTraining = { trainingId -> viewModel.deleteTraining(trainingId) },
            onAddExercise = { trainingId -> onAddExercise(planId, trainingId) },
            onEditExercise = { trainingId, exerciseId ->
                onEditExercise(planId, trainingId, exerciseId)
            },
            onDeleteExercise = { trainingId, exerciseId ->
                viewModel.deleteExercise(trainingId, exerciseId)
            },
            onReorderExercises = { trainingId, exercisesIds ->
                viewModel.reorderExercises(trainingId, exercisesIds)
            }
        )
    }
}
