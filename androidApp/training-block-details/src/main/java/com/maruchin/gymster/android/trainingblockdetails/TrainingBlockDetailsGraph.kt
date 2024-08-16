package com.maruchin.gymster.android.trainingblockdetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.maruchin.gymster.android.trainingblockdetails.progressform.ProgressFormRoute
import com.maruchin.gymster.android.trainingblockdetails.progressform.progressFormScreen
import com.maruchin.gymster.android.trainingblockdetails.timeline.TimelineRoute
import com.maruchin.gymster.android.trainingblockdetails.timeline.timelineScreen
import kotlinx.serialization.Serializable

@Serializable
data class TrainingBlockDetailsGraph(val trainingBlockId: String)

fun NavGraphBuilder.trainingBlockDetailsGraph(navController: NavController) {
    navigation<TrainingBlockDetailsGraph>(startDestination = TimelineRoute) {
        timelineScreen(
            navController = navController,
            onBack = { navController.navigateUp() },
            onEditProgress = { trainingBlockId, weekNumber, trainingId, exerciseId, progressIndex ->
                navController.navigate(
                    ProgressFormRoute(
                        trainingBlockId,
                        weekNumber,
                        trainingId,
                        exerciseId,
                        progressIndex
                    )
                )
            }
        )
        progressFormScreen(
            onBack = { navController.navigateUp() }
        )
    }
}
