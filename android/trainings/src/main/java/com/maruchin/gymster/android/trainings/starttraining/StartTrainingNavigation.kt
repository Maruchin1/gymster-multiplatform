package com.maruchin.gymster.android.trainings.starttraining

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.gymster.shared.feature.trainings.starttraining.StartTrainingViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object StartTrainingScreen

fun NavController.navigateToStartTraining() {
    navigate(StartTrainingScreen)
}

fun NavGraphBuilder.startTrainingScreen(onBack: () -> Unit, onEditPlans: () -> Unit) {
    composable<StartTrainingScreen> {
        val viewModel = viewModel { StartTrainingViewModel() }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(state) {
            if (state.isCreated) onBack()
        }

        StartTrainingScreen(
            state = state,
            onBack = onBack,
            onEditPlans = onEditPlans,
            onSelectPlan = viewModel::selectPlan,
            onResetPlan = viewModel::resetPlan,
            onSelectTraining = viewModel::selectTraining,
            onResetTraining = viewModel::resetTraining,
            onSelectDate = viewModel::selectDate,
            onResetDate = viewModel::resetDate,
            onStart = viewModel::startTraining
        )
    }
}
