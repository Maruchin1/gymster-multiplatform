package com.maruchin.gymster.android.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.maruchin.gymster.android.home.home.HomeRoute
import com.maruchin.gymster.android.home.home.homeScreen
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
