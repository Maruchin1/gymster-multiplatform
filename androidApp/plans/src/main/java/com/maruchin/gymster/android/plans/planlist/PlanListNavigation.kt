package com.maruchin.gymster.android.plans.planlist

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.gymster.feature.plans.planlist.PlanListViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object PlanListRoute

internal fun NavGraphBuilder.planListScreen(
    onAddTrainingPlan: () -> Unit,
    onEditTrainingPlan: (String) -> Unit
) {
    composable<PlanListRoute> {
        val viewModel = viewModel { PlanListViewModel.get() }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        PlanListScreen(
            state = state,
            onAddTrainingPlan = onAddTrainingPlan,
            onEditTrainingPlan = onEditTrainingPlan
        )
    }
}
