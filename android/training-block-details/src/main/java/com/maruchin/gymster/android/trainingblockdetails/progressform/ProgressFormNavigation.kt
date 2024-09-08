package com.maruchin.gymster.android.trainingblockdetails.progressform

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.maruchin.gymster.feature.trainingblockdetails.progressform.ProgressFormViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data class ProgressFormRoute(val trainingBlockId: String, val setProgressId: String)

internal fun NavGraphBuilder.progressFormScreen(onBack: () -> Unit) {
    composable<ProgressFormRoute> { entry ->
        val (trainingBlockId, setProgressId) = entry.toRoute<ProgressFormRoute>()
        val viewModel = viewModel {
            ProgressFormViewModel.create(trainingBlockId, setProgressId)
        }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        ProgressFormScreen(
            state = state,
            onBack = onBack,
            onSave = { viewModel.saveProgress(it).invokeOnCompletion { onBack() } }
        )
    }
}
