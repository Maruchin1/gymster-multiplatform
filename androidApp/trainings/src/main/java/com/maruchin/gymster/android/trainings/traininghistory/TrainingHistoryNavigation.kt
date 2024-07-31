package com.maruchin.gymster.android.trainings.traininghistory

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.gymster.feature.trainings.traininghistory.TrainingHistoryViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object TrainingHistoryRoute

internal fun NavGraphBuilder.trainingHistoryScreen(
    onCreateTraining: () -> Unit,
    onOpenTraining: (trainingId: String) -> Unit
) {
    composable<TrainingHistoryRoute> {
        val viewModel = viewModel { TrainingHistoryViewModel.get() }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        TrainingHistoryScreen(
            state = state,
            onCreateTraining = onCreateTraining,
            onOpenTraining = onOpenTraining
        )
    }
}
