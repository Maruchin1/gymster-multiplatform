package com.maruchin.gymster.android.trainings.trainingblockeditor

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.maruchin.gymster.android.ui.BASE_URI
import com.maruchin.gymster.feature.trainings.trainingblockeditor.TrainingBlockEditorViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data class TrainingBlockEditorRoute(val trainingBlockId: String)

internal fun NavController.navigateToTrainingBlockEditor(trainingBlockId: String) {
    navigate(TrainingBlockEditorRoute(trainingBlockId))
}

internal fun NavGraphBuilder.trainingBlockEditorScreen(
    onBack: () -> Unit,
    onOpenTraining: (
        trainingBlockId: String,
        weekIndex: Int,
        trainingIndex: Int,
        exerciseIndex: Int
    ) -> Unit
) {
    composable<TrainingBlockEditorRoute>(
        deepLinks = listOf(
            navDeepLink<TrainingBlockEditorRoute>(basePath = "$BASE_URI/training-block")
        )
    ) { entry ->
        val (trainingBlockId) = entry.toRoute<TrainingBlockEditorRoute>()
        val viewModel = viewModel { TrainingBlockEditorViewModel(trainingBlockId) }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        TrainingBlockEditorScreen(
            state = state,
            onBack = onBack,
            onOpenTraining = { weekIndex, trainingIndex, exerciseIndex ->
                onOpenTraining(trainingBlockId, weekIndex, trainingIndex, exerciseIndex)
            }
        )
    }
}
