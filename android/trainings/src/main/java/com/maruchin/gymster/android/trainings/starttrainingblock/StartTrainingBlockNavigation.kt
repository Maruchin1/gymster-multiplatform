package com.maruchin.gymster.android.trainings.starttrainingblock

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.gymster.feature.trainings.starttrainingblock.StartTrainingBlockViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object StartTrainingBlockRoute

internal fun NavController.navigateToStartTrainingBlock() {
    navigate(StartTrainingBlockRoute)
}

internal fun NavGraphBuilder.startTrainingBlockScreen(onBack: () -> Unit, onEditPlans: () -> Unit) {
    composable<StartTrainingBlockRoute> {
        val viewModel = viewModel { StartTrainingBlockViewModel() }
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        val currentOnBack by rememberUpdatedState(onBack)

        LaunchedEffect(state) {
            if (state.isCreated) currentOnBack()
        }

        StartTrainingBlockScreen(
            state = state,
            onBack = onBack,
            onEditPlans = onEditPlans,
            onSelectPlan = viewModel::selectPlan,
            onResetPlan = viewModel::resetPlan,
            onSelectStartDate = viewModel::selectStarDate,
            onResetStartDate = viewModel::resetStartDate,
            onSelectWeeksDuration = viewModel::selectWeeksDuration,
            onResetWeeksDuration = viewModel::resetWeeksDuration,
            onStart = viewModel::startTrainingBlock
        )
    }
}
