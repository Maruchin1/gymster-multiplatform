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
    onBack: () -> Unit,
    onOpenTrainingBlock: (trainingBlockId: String) -> Unit,
    onStartTrainingBlock: () -> Unit
) {
    composable<TrainingBlockListRoute> {
        val viewModel = viewModel { TrainingBlockListViewModel() }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        TrainingBlockListScreen(
            state = state,
            onBack = onBack,
            onOpenTrainingBlock = onOpenTrainingBlock,
            onStartTrainingBlock = onStartTrainingBlock
        )
    }
}
