package com.maruchin.multiplatform.gymster.android.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.maruchin.multiplatform.gymster.android.home.home.HomeRoute
import com.maruchin.multiplatform.gymster.android.home.home.homeScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeGraph

fun NavGraphBuilder.homeGraph(onOpenTrainingPlans: () -> Unit) {
    navigation<HomeGraph>(startDestination = HomeRoute) {
        homeScreen(
            onOpenTrainingPlans = onOpenTrainingPlans
        )
    }
}
