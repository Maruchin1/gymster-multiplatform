package com.maruchin.gymster.android.trainings.trainingblocklist

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.gymster.feature.trainings.trainingblocklist.TrainingBlockListViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object TrainingBlockListRoute

internal fun NavGraphBuilder.trainingBlockListScreen(
    onAddTrainingBlock: () -> Unit,
    onOpenTrainingBlock: (trainingBlockId: String) -> Unit
) {
    composable<TrainingBlockListRoute> {
        val viewModel = viewModel { TrainingBlockListViewModel.get() }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        TrainingBlockListScreen(
            state = state,
            onAddTrainingBlock = onAddTrainingBlock,
            onOpenTrainingBlock = onOpenTrainingBlock
        )
    }
}
