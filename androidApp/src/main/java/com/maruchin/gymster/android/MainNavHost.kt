package com.maruchin.gymster.android

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.maruchin.gymster.android.home.HomeGraph
import com.maruchin.gymster.android.home.homeGraph
import com.maruchin.gymster.android.planeditor.PlanEditorGraph
import com.maruchin.gymster.android.planeditor.planEditorGraph
import com.maruchin.gymster.android.planlist.PlanListGraph
import com.maruchin.gymster.android.planlist.planListGraph

@Composable
internal fun MainNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeGraph,
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        homeGraph(
            onOpenPlans = { navController.navigate(PlanListGraph) }
        )
        planListGraph(
            navController = navController,
            onEditPlan = { planId -> navController.navigate(PlanEditorGraph(planId)) }
        )
        planEditorGraph(navController = navController)
    }
}
