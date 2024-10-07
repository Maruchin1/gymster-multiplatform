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
import com.maruchin.gymster.android.trainings.navigateToTrainings
import com.maruchin.gymster.android.trainings.trainingsGraph
import com.maruchin.gymster.android.trainings2.navigateToTrainings2
import com.maruchin.gymster.android.trainings2.trainings2Graph

@Composable
internal fun MainNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeGraph,
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        homeGraph(
            onOpenPlans = { navController.navigate(PlansRoute) },
            onOpenTrainings = navController::navigateToTrainings2,
            onOpenTrainingBlock = navController::navigateToTrainings,
            onOpenTraining = navController::navigateToTrainings
        )
        plansGraph(navController)
        trainings2Graph(navController)
        trainingsGraph(
            navController = navController,
            onEditPlans = { navController.navigate(PlansRoute) }
        )
    }
}
