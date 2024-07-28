package com.maruchin.multiplatform.gymster.android.trainingplans.planlist

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.planlist.PlanListViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object PlanListRoute

internal fun NavGraphBuilder.planListScreen(
    onBack: () -> Unit,
    onAddTrainingPlan: () -> Unit,
    onEditTrainingPlan: (String) -> Unit
) {
    composable<PlanListRoute> {
        val viewModel = viewModel { PlanListViewModel.get() }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        PlanListScreen(
            state = state,
            onBack = onBack,
            onAddTrainingPlan = onAddTrainingPlan,
            onEditTrainingPlan = onEditTrainingPlan
        )
    }
}
