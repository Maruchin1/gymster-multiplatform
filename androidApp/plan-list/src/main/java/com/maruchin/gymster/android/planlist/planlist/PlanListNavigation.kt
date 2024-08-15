package com.maruchin.gymster.android.planlist.planlist

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.gymster.planlist.planlist.PlanListViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object PlanListRoute

internal fun NavGraphBuilder.planListScreen(
    onBack: () -> Unit,
    onAddPlan: () -> Unit,
    onStartTrainingBlock: (planId: String) -> Unit,
    onEditPlan: (String) -> Unit
) {
    composable<PlanListRoute> {
        val viewModel = viewModel { PlanListViewModel.get() }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        PlanListScreen(
            state = state,
            onBack = onBack,
            onAddPlan = onAddPlan,
            onStartTrainingBlock = onStartTrainingBlock,
            onEditPlan = onEditPlan
        )
    }
}
