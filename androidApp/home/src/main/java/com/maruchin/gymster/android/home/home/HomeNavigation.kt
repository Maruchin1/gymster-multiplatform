package com.maruchin.gymster.android.home.home

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.gymster.feature.home.home.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object HomeRoute

internal fun NavGraphBuilder.homeScreen(
    onOpenPlans: () -> Unit,
    onOpenTrainingBlock: (trainingBlockId: String) -> Unit
) {
    composable<HomeRoute> {
        val viewModel = viewModel { HomeViewModel.get() }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        HomeScreen(
            state = state,
            onOpenPlans = onOpenPlans,
            onOpenTrainingBlock = onOpenTrainingBlock
        )
    }
}
