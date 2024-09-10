package com.maruchin.gymster.android.app

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.maruchin.gymster.android.home.HomeGraph
import com.maruchin.gymster.android.home.homeGraph
import com.maruchin.gymster.android.plans.PlansRoute
import com.maruchin.gymster.android.plans.plansGraph
import com.maruchin.gymster.android.trainingblockdetails.trainingBlockDetailsGraph
import com.maruchin.gymster.android.trainingeditor.TrainingEditorGraph
import com.maruchin.gymster.android.trainingeditor.trainingEditorGraph

@Composable
internal fun MainNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeGraph,
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        homeGraph(
            onOpenPlans = { navController.navigate(PlansRoute) }
        )
        plansGraph(navController)
        trainingBlockDetailsGraph(
            navController = navController,
            onOpenTraining = { trainingBlockId, trainingId ->
                navController.navigate(TrainingEditorGraph(trainingBlockId, trainingId))
            }
        )
        trainingEditorGraph(navController)
    }
}
