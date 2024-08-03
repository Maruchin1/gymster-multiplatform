package com.maruchin.gymster.android.trainings.progresseditor

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.toRoute
import com.maruchin.gymster.android.ui.fullScreenDialog
import com.maruchin.gymster.feature.trainings.progresseditor.ProgressEditorViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data class ProgressEditorRoute(
    val trainingId: String,
    val exerciseId: String,
    val progressIndex: Int
)

internal fun NavGraphBuilder.progressEditorDialog(onClose: () -> Unit) {
    fullScreenDialog<ProgressEditorRoute> { entry ->
        val (trainingId, exerciseId, progressIndex) = entry.toRoute<ProgressEditorRoute>()
        val viewModel = viewModel {
            ProgressEditorViewModel.get(trainingId, exerciseId, progressIndex)
        }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        ProgressEditorDialog(
            state = state,
            onClose = onClose,
            onSave = { viewModel.saveProgress(it).invokeOnCompletion { onClose() } }
        )
    }
}
