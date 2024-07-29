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

internal fun NavGraphBuilder.planEditorScreen(
    onBack: () -> Unit,
    onEditName: (planId: String) -> Unit,
    onAddDay: (planId: String) -> Unit,
    onEditDay: (planId: String, dayId: String) -> Unit,
    onAddExercise: (planId: String, dayId: String) -> Unit,
    onEditExercise: (planId: String, dayId: String, exerciseId: String) -> Unit
) {
    composable<PlanEditorRoute> {
        val (planId) = it.toRoute<PlanEditorRoute>()
        val viewModel = viewModel {
            PlanEditorViewModel.get(planId = planId)
        }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        PlanEditorScreen(
            state = state,
            onBack = onBack,
            onEditName = { onEditName(planId) },
            onDeletePlan = { viewModel.deletePlan().invokeOnCompletion { onBack() } },
            onAddDay = { onAddDay(planId) },
            onEditDay = { dayId -> onEditDay(planId, dayId) },
            onDeleteDay = { dayId -> viewModel.deleteDay(dayId) },
            onAddExercise = { dayId -> onAddExercise(planId, dayId) },
            onEditExercise = { dayId, exerciseId -> onEditExercise(planId, dayId, exerciseId) },
            onDeleteExercise = { dayId, exerciseId -> viewModel.deleteExercise(dayId, exerciseId) },
            onReorderExercises = { dayId, exercisesIds ->
                viewModel.reorderExercises(dayId, exercisesIds)
            }
        )
    }
}
