package com.maruchin.gymster.android.trainings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data object TrainingsRoute

@Serializable
private data object TrainingBlockListRoute

@Serializable
private data object StartTrainingBlockRoute

@Serializable
private data class TrainingBlockEditorRoute(val trainingBlockId: String)

fun NavGraphBuilder.trainingsGraph(navController: NavController, onEditPlans: () -> Unit) {
    navigation<TrainingsRoute>(startDestination = TrainingBlockListRoute) {
        composable<TrainingBlockListRoute> {
            TrainingBlockListScreen(
                onBack = { navController.navigateUp() },
                onOpenTrainingBlock = { navController.navigate(TrainingBlockEditorRoute(it)) },
                onStartTrainingBlock = { navController.navigate(StartTrainingBlockRoute) }
            )
        }
        composable<StartTrainingBlockRoute> {
            StartTrainingBlockScreen(
                onBack = { navController.navigateUp() },
                onEditPlans = onEditPlans
            )
        }
        composable<TrainingBlockEditorRoute> {
            val route = it.toRoute<TrainingBlockEditorRoute>()
            TrainingBlockEditorScreen(
                trainingBlockId = route.trainingBlockId,
                onBack = { navController.navigateUp() },
                onEditProgress = { }, // TODO Move progress editor to this module
                onOpenTraining = { } // TODO Move training editor to this module
            )
        }
    }
}
