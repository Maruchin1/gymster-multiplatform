package com.maruchin.gymster.android.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
internal data object HomeRoute

internal fun NavGraphBuilder.homeScreen(onOpenPlans: () -> Unit, onOpenTrainings: () -> Unit) {
    composable<HomeRoute> {
        HomeScreen(
            onOpenPlans = onOpenPlans,
            onOpenTrainings = onOpenTrainings
        )
    }
}
