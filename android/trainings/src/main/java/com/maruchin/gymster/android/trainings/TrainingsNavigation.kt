package com.maruchin.gymster.android.trainings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

// TODO Move TrainingBlockEditor to this module

@Serializable
data object TrainingsRoute

@Serializable
private data object TrainingBlockListRoute

@Serializable
private data object StartTrainingBlockRoute

fun NavGraphBuilder.trainingsGraph(navController: NavController) {
    navigation<TrainingsRoute>(startDestination = TrainingBlockListRoute) {
        composable<TrainingBlockListRoute> {
            TrainingBlockListScreen(
                onBack = { navController.navigateUp() },
                onOpenTrainingBlock = { },
                onStartTrainingBlock = { navController.navigate(StartTrainingBlockRoute) }
            )
        }
        composable<StartTrainingBlockRoute> {
            StartTrainingBlockScreen(
                onBack = { navController.navigateUp() }
            )
        }
    }
}
