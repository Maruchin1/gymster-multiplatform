package com.maruchin.gymster.android.trainings.trainingeditor

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.maruchin.gymster.feature.trainings.trainingeditor.TrainingEditorViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data class TrainingEditorRoute(
    val trainingBlockId: String,
    val weekIndex: Int,
    val trainingIndex: Int,
    val exerciseIndex: Int
)

internal fun NavController.navigateToTrainingEditor(
    trainingBlockId: String,
    weekIndex: Int,
    trainingIndex: Int,
    exerciseIndex: Int
) {
    navigate(
        TrainingEditorRoute(
            trainingBlockId = trainingBlockId,
            weekIndex = weekIndex,
            trainingIndex = trainingIndex,
            exerciseIndex = exerciseIndex
        )
    )
}

internal fun NavGraphBuilder.trainingEditorScreen(onBack: () -> Unit) {
    composable<TrainingEditorRoute> { entry ->
        val (trainingBlockId, weekIndex, trainingIndex, exerciseIndex) =
            entry.toRoute<TrainingEditorRoute>()
        val viewModel = viewModel {
            TrainingEditorViewModel(trainingBlockId, weekIndex, trainingIndex, exerciseIndex)
        }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        TrainingEditorScreen(
            state = state,
            onBack = onBack,
            onUpdateWeight = viewModel::updateWeight,
            onUpdateReps = viewModel::updateReps
        )
    }
}
