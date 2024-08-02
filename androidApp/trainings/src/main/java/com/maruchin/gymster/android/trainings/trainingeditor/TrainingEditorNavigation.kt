package com.maruchin.gymster.android.trainings.trainingeditor

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.maruchin.gymster.feature.trainings.trainingeditor.TrainingEditorViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data class TrainingEditorRoute(val trainingId: String)

internal fun NavGraphBuilder.trainingEditorScreen(
    onBack: () -> Unit,
    onEditProgress: (trainingId: String, exerciseId: String, progressIndex: Int) -> Unit
) {
    composable<TrainingEditorRoute> {
        val (trainingId) = it.toRoute<TrainingEditorRoute>()
        val viewModel = viewModel { TrainingEditorViewModel.get(trainingId) }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        TrainingEditorScreen(
            state = state,
            onBack = onBack,
            onEditProgress = { exerciseId, progressIndex ->
                onEditProgress(trainingId, exerciseId, progressIndex)
            }
        )
    }
}
