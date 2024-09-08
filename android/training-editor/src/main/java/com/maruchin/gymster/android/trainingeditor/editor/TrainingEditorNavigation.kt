package com.maruchin.gymster.android.trainingeditor.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.maruchin.gymster.android.trainingeditor.TrainingEditorGraph
import com.maruchin.gymster.feature.trainingeditor.editor.TrainingEditorViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object TrainingEditorRoute

internal fun NavGraphBuilder.trainingEditorScreen(
    navController: NavController,
    onBack: () -> Unit
) {
    composable<TrainingEditorRoute> {
        val (trainingBlockId, trainingId) = remember(navController) {
            navController.getBackStackEntry<TrainingEditorGraph>().toRoute<TrainingEditorGraph>()
        }
        val viewModel = viewModel { TrainingEditorViewModel.create(trainingBlockId, trainingId) }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        TrainingEditorScreen(
            state = state,
            onBack = onBack,
            onUpdateSetProgress = { setProgressId, progress ->
                viewModel.updateSetProgress(setProgressId, progress)
            }
        )
    }
}
