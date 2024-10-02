package com.maruchin.gymster.android.trainings

import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.maruchin.gymster.android.trainings.starttrainingblock.navigateToStartTrainingBlock
import com.maruchin.gymster.android.trainings.starttrainingblock.startTrainingBlockScreen
import com.maruchin.gymster.android.trainings.trainingblockeditor.navigateToTrainingBlockEditor
import com.maruchin.gymster.android.trainings.trainingblockeditor.trainingBlockEditorScreen
import com.maruchin.gymster.android.trainings.trainingblocklist.TrainingBlockListRoute
import com.maruchin.gymster.android.trainings.trainingblocklist.trainingBlockListScreen
import com.maruchin.gymster.android.trainings.trainingeditor.navigateToTrainingEditor
import com.maruchin.gymster.android.trainings.trainingeditor.trainingEditorScreen
import com.maruchin.gymster.android.ui.BASE_URI
import kotlinx.serialization.Serializable

@Serializable
data object TrainingsRoute

fun NavController.navigateToTrainings() {
    navigate(TrainingsRoute)
}

fun NavController.navigateToTrainings(trainingBlockId: String) {
    navigate("$BASE_URI/training-block/$trainingBlockId".toUri())
}

fun NavController.navigateToTrainings(trainingBlockId: String, weekIndex: Int, trainingIndex: Int) {
    navigate(
        "$BASE_URI/training/$trainingBlockId/$weekIndex/$trainingIndex".toUri()
    )
}

fun NavGraphBuilder.trainingsGraph(navController: NavController, onEditPlans: () -> Unit) {
    navigation<TrainingsRoute>(startDestination = TrainingBlockListRoute) {
        trainingBlockListScreen(
            onBack = { navController.navigateUp() },
            onOpenTrainingBlock = { navController.navigateToTrainingBlockEditor(it) },
            onStartTrainingBlock = { navController.navigateToStartTrainingBlock() }
        )
        startTrainingBlockScreen(
            onBack = { navController.navigateUp() },
            onEditPlans = onEditPlans
        )
        trainingBlockEditorScreen(
            onBack = { navController.navigateUp() },
            onOpenTraining = navController::navigateToTrainingEditor
        )
        trainingEditorScreen(
            onBack = { navController.navigateUp() }
        )
    }
}
