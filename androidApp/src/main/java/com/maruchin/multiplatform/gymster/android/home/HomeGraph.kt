package com.maruchin.multiplatform.gymster.android.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.maruchin.multiplatform.gymster.android.home.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
internal object HomeGraph

internal fun NavGraphBuilder.homeGraph(onOpenTrainingPlans: () -> Unit) {
    navigation<HomeGraph>(startDestination = HomeRoute) {
        composable<HomeRoute> {
            HomeRoute(
                onOpenTrainingPlans = onOpenTrainingPlans
            )
        }
    }
}
