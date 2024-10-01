package com.maruchin.gymster.android.plans

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

// TODO Split navigation classes into separate files

@Serializable
data object PlansRoute

@Serializable
private data object PlanListRoute

@Serializable
private data class PlanEditorRoute(val planId: String)

fun NavGraphBuilder.plansGraph(navController: NavController) {
    navigation<PlansRoute>(startDestination = PlanListRoute) {
        composable<PlanListRoute> {
            PlanListScreen(
                onBack = { navController.navigateUp() },
                onOpenPlan = { navController.navigate(PlanEditorRoute(it)) }
            )
        }
        composable<PlanEditorRoute> {
            val route = it.toRoute<PlanEditorRoute>()
            PlanEditorScreen(
                planId = route.planId,
                onBack = { navController.navigateUp() }
            )
        }
    }
}
