package com.maruchin.gymster.android.plans

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.maruchin.gymster.android.plans.planeditor.PlanEditorRoute
import com.maruchin.gymster.android.plans.planeditor.planEditorScreen
import com.maruchin.gymster.android.plans.planlist.PlanListRoute
import com.maruchin.gymster.android.plans.planlist.planListScreen
import kotlinx.serialization.Serializable

@Serializable
data object PlansRoute

fun NavGraphBuilder.plansGraph(navController: NavController) {
    navigation<PlansRoute>(startDestination = PlanListRoute) {
        planListScreen(
            onBack = { navController.navigateUp() },
            onOpenPlan = { navController.navigate(PlanEditorRoute(it)) }
        )
        planEditorScreen(
            onBack = { navController.navigateUp() }
        )
    }
}
