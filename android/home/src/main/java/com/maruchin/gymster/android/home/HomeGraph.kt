package com.maruchin.gymster.android.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable
object HomeGraph

fun NavGraphBuilder.homeGraph(onOpenPlans: () -> Unit, onOpenTrainings: () -> Unit) {
    navigation<HomeGraph>(startDestination = HomeRoute) {
        homeScreen(
            onOpenPlans = onOpenPlans,
            onOpenTrainings = onOpenTrainings
        )
    }
}
