package com.maruchin.gymster.android.trainings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.maruchin.gymster.android.trainings.traininghistory.TrainingHistoryRoute
import com.maruchin.gymster.android.trainings.traininghistory.trainingHistoryScreen
import kotlinx.serialization.Serializable

@Serializable
data object TrainingsGraph

fun NavGraphBuilder.trainingsGraph(navController: NavController) {
    navigation<TrainingsGraph>(startDestination = TrainingHistoryRoute) {
        trainingHistoryScreen(
            onCreateTraining = {
            },
            onOpenTraining = {
            }
        )
    }
}
