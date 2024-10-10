package com.maruchin.gymster.android.trainings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.maruchin.gymster.android.trainings.starttraining.navigateToStartTraining
import com.maruchin.gymster.android.trainings.starttraining.startTrainingScreen
import com.maruchin.gymster.android.trainings.trainingeditor.navigateToTrainingEditor
import com.maruchin.gymster.android.trainings.trainingeditor.trainingEditorScreen
import com.maruchin.gymster.android.trainings.traininglist.TrainingListScreen
import com.maruchin.gymster.android.trainings.traininglist.trainingListScreen
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
            onOpenTraining = { navController.navigateToTrainingEditor(it) },
            onStartTraining = { navController.navigateToStartTraining() }
        )
        startTrainingScreen(
            onBack = { navController.navigateUp() },
            onEditPlans = { }
        )
        trainingEditorScreen(
            onBack = { navController.navigateUp() }
        )
    }
}
