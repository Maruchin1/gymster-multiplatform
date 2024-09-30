package com.maruchin.gymster.android.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable
object HomeGraph

@Serializable
internal data object HomeRoute

fun NavGraphBuilder.homeGraph(onOpenPlans: () -> Unit, onOpenTrainings: () -> Unit) {
    navigation<HomeGraph>(startDestination = HomeRoute) {
        composable<HomeRoute> {
            HomeScreen(
                onOpenPlans = onOpenPlans,
                onOpenActiveTrainingBlock = { }, // TODO Navigate to active training block
                onOpenTrainings = onOpenTrainings
            )
        }
    }
}
