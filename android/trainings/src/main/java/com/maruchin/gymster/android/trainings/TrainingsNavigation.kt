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

@Serializable
private data class TrainingEditorRoute(
    val trainingBlockId: String,
    val weekIndex: Int,
    val trainingIndex: Int,
    val exerciseIndex: Int
)

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
            val (trainingBlockId) = it.toRoute<TrainingBlockEditorRoute>()
            TrainingBlockEditorScreen(
                trainingBlockId = trainingBlockId,
                onBack = { navController.navigateUp() },
                onOpenTraining = { weekIndex, trainingIndex, exerciseIndex ->
                    navController.navigate(
                        TrainingEditorRoute(
                            trainingBlockId,
                            weekIndex,
                            trainingIndex,
                            exerciseIndex
                        )
                    )
                }
            )
        }
        composable<TrainingEditorRoute> {
            val (trainingBlockId, weekIndex, trainingIndex, exerciseIndex) =
                it.toRoute<TrainingEditorRoute>()
            TrainingEditorScreen(
                trainingBlockId = trainingBlockId,
                weekIndex = weekIndex,
                trainingIndex = trainingIndex,
                exerciseIndex = exerciseIndex,
                onBack = { navController.navigateUp() }
            )
        }
    }
}
