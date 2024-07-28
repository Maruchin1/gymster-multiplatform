package com.maruchin.multiplatform.gymster.android

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.maruchin.multiplatform.gymster.android.home.HomeGraph
import com.maruchin.multiplatform.gymster.android.home.homeGraph
import com.maruchin.multiplatform.gymster.android.trainingplans.TrainingPlansGraph
import com.maruchin.multiplatform.gymster.android.trainingplans.trainingPlansGraph

@Composable
internal fun MainNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HomeGraph) {
        homeGraph(
            onOpenTrainingPlans = {
                navController.navigate(TrainingPlansGraph)
            }
        )
        trainingPlansGraph(navController = navController)
    }
}
