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
internal data class ProgressFormRoute(
    val trainingBlockId: String,
    val weekNumber: Int,
    val trainingId: String,
    val exerciseId: String,
    val progressIndex: Int
)

internal fun NavGraphBuilder.progressFormScreen(onBack: () -> Unit) {
    composable<ProgressFormRoute> { entry ->
        val (trainingBlockId, weekNumber, trainingId, exerciseId, progressIndex) =
            entry.toRoute<ProgressFormRoute>()
        val viewModel = viewModel {
            ProgressFormViewModel.get(
                trainingBlockId,
                weekNumber,
                trainingId,
                exerciseId,
                progressIndex
            )
        }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        ProgressFormScreen(
            state = state,
            onBack = onBack,
            onSave = { viewModel.saveProgress(it).invokeOnCompletion { onBack() } }
        )
    }
}
