package com.maruchin.gymster.android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.maruchin.gymster.android.home.HomeGraph
import com.maruchin.gymster.android.home.homeGraph
import com.maruchin.gymster.android.plans.TrainingPlansGraph
import com.maruchin.gymster.android.plans.trainingPlansGraph

@Composable
internal fun MainNavHost() {
    val navController = rememberNavController()

    Column {
        NavHost(
            navController = navController,
            startDestination = HomeGraph,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .weight(1f)
        ) {
            homeGraph(
                onOpenTrainingPlans = {
                    navController.navigate(TrainingPlansGraph)
                }
            )
            trainingPlansGraph(navController = navController)
        }
        MainNavigationBar(navController = navController)
    }
}