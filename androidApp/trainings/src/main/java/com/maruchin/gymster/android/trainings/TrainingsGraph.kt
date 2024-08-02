package com.maruchin.gymster.android.trainings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.maruchin.gymster.android.trainings.planpicker.PlanPickerRoute
import com.maruchin.gymster.android.trainings.planpicker.planPickerDialog
import com.maruchin.gymster.android.trainings.trainingeditor.TrainingEditorRoute
import com.maruchin.gymster.android.trainings.trainingeditor.trainingEditorScreen
import com.maruchin.gymster.android.trainings.traininghistory.TrainingHistoryRoute
import com.maruchin.gymster.android.trainings.traininghistory.trainingHistoryScreen
import kotlinx.serialization.Serializable

@Serializable
data object TrainingsGraph

fun NavGraphBuilder.trainingsGraph(navController: NavController) {
    navigation<TrainingsGraph>(startDestination = TrainingHistoryRoute) {
        trainingHistoryScreen(
            onCreateTraining = {
                navController.navigate(PlanPickerRoute)
            },
            onOpenTraining = { trainingId ->
                navController.navigate(TrainingEditorRoute(trainingId))
            }
        )
        planPickerDialog(
            onClose = {
                navController.navigateUp()
            }
        )
        trainingEditorScreen(
            onBack = {
                navController.navigateUp()
            },
            onEditProgress = { _, _, _ ->
            }
        )
    }
}
