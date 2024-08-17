package com.maruchin.gymster.android.trainingblockdetails.timeline

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.maruchin.gymster.android.trainingblockdetails.TrainingBlockDetailsGraph
import com.maruchin.gymster.feature.trainingblockdetails.timeline.TimelineViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object TimelineRoute

internal fun NavGraphBuilder.timelineScreen(
    navController: NavController,
    onBack: () -> Unit,
    onEditProgress: (trainingBlockId: String, setProgressId: String) -> Unit
) {
    composable<TimelineRoute> {
        val (trainingBlockId) = remember(navController) {
            navController.getBackStackEntry<TrainingBlockDetailsGraph>()
                .toRoute<TrainingBlockDetailsGraph>()
        }
        val viewModel = viewModel { TimelineViewModel.get(trainingBlockId) }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        TimelineScreen(
            state = state,
            onBack = onBack,
            onEditProgress = { setProgressId ->
                onEditProgress(trainingBlockId, setProgressId)
            }
        )
    }
}
