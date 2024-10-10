package com.maruchin.gymster.android.trainings2.trainingeditor

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.maruchin.gymster.shared.feature.trainings2.trainingeditor.TrainingEditorViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data class TrainingEditorScreen(val trainingId: String)

internal fun NavController.navigateToTrainingEditor(trainingId: String) {
    navigate(TrainingEditorScreen(trainingId))
}

internal fun NavGraphBuilder.trainingEditorScreen(onBack: () -> Unit) {
    composable<TrainingEditorScreen> { entry ->
        val (trainingId) = entry.toRoute<TrainingEditorScreen>()
        val viewModel = viewModel { TrainingEditorViewModel(trainingId) }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        TrainingEditorScreen(
            state = state,
            onBack = onBack,
            onUpdateWeight = viewModel::updateWeight,
            onUpdateReps = viewModel::updateReps
        )
    }
}
