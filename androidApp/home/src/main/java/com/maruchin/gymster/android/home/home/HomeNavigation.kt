package com.maruchin.gymster.android.home.home

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maruchin.gymster.feature.home.home.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object HomeRoute

internal fun NavGraphBuilder.homeScreen() {
    composable<HomeRoute> {
        val viewModel = viewModel { HomeViewModel.get() }

        HomeScreen(
            onSeedData = { viewModel.seedData() }
        )
    }
}
