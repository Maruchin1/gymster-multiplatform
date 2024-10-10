package com.maruchin.gymster.android.trainings.traininglist

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.gymster.shared.feature.trainings.traininglist.TrainingListViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object TrainingListScreen

internal fun NavGraphBuilder.trainingListScreen(
    onBack: () -> Unit,
    onOpenTraining: (String) -> Unit,
    onStartTraining: () -> Unit
) {
    composable<TrainingListScreen> {
        val viewModel = viewModel { TrainingListViewModel() }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        TrainingListScreen(
            state = state,
            onBack = onBack,
            onOpenTraining = onOpenTraining,
            onStartTraining = onStartTraining
        )
    }
}
