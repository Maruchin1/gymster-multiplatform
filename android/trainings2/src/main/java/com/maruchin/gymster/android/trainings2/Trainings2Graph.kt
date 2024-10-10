package com.maruchin.gymster.android.trainings2

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.maruchin.gymster.android.trainings2.starttraining.navigateToStartTraining
import com.maruchin.gymster.android.trainings2.starttraining.startTrainingScreen
import com.maruchin.gymster.android.trainings2.traininglist.TrainingListScreen
import com.maruchin.gymster.android.trainings2.traininglist.trainingListScreen
import kotlinx.serialization.Serializable

@Serializable
data object Trainings2Graph

fun NavController.navigateToTrainings2() {
    navigate(Trainings2Graph)
}

fun NavGraphBuilder.trainings2Graph(navController: NavController) {
    navigation<Trainings2Graph>(startDestination = TrainingListScreen) {
        trainingListScreen(
            onBack = { navController.navigateUp() },
            onOpenTraining = { },
            onStartTraining = { navController.navigateToStartTraining() }
        )
        startTrainingScreen(
            onBack = { navController.navigateUp() },
            onEditPlans = {}
        )
    }
}
