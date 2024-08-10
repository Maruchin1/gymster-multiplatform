package com.maruchin.gymster.android.trainings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import kotlinx.serialization.Serializable

@Serializable
data object TrainingsGraph

fun NavGraphBuilder.trainingsGraph(navController: NavController) {
//    navigation<TrainingsGraph>(startDestination = TrainingHistoryRoute) {
//        trainingEditorScreen(
//            onBack = {
//                navController.navigateUp()
//            },
//            onEditProgress = { trainingId, exerciseId, progressIndex ->
//                navController.navigate(ProgressEditorRoute(trainingId, exerciseId, progressIndex))
//            }
//        )
//        progressEditorDialog(
//            onClose = {
//                navController.navigateUp()
//            }
//        )
//    }
}
