package com.maruchin.gymster.android.trainings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.maruchin.gymster.android.trainings.trainingblocklist.TrainingBlockListRoute
import com.maruchin.gymster.android.trainings.trainingblocklist.trainingBlockListScreen
import kotlinx.serialization.Serializable

@Serializable
data object TrainingsGraph

fun NavGraphBuilder.trainingsGraph(navController: NavController) {
    navigation<TrainingsGraph>(startDestination = TrainingBlockListRoute) {
        trainingBlockListScreen(
            onAddTrainingBlock = {},
            onOpenTrainingBlock = {}
        )
    }
}
