package com.maruchin.gymster.android.home.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
internal data object HomeRoute

internal fun NavGraphBuilder.homeScreen(onOpenPlans: () -> Unit) {
    composable<HomeRoute> {
        HomeScreen(
            onOpenPlans = onOpenPlans
        )
    }
}
