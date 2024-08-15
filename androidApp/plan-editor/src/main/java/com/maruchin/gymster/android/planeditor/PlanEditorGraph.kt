package com.maruchin.gymster.android.planeditor

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.maruchin.gymster.android.planeditor.durationform.DurationFormRoute
import com.maruchin.gymster.android.planeditor.durationform.durationFormDialog
import com.maruchin.gymster.android.planeditor.exerciseform.ExerciseFormRoute
import com.maruchin.gymster.android.planeditor.exerciseform.exerciseFormDialog
import com.maruchin.gymster.android.planeditor.planeditor.PlanEditorRoute
import com.maruchin.gymster.android.planeditor.planeditor.planEditorScreen
import com.maruchin.gymster.android.planeditor.planform.PlanFormRoute
import com.maruchin.gymster.android.planeditor.planform.planFormDialog
import com.maruchin.gymster.android.planeditor.trainingform.TrainingFormRoute
import com.maruchin.gymster.android.planeditor.trainingform.trainingFormDialog
import kotlinx.serialization.Serializable

@Serializable
data class PlanEditorGraph(val planId: String)

fun NavGraphBuilder.planEditorGraph(navController: NavController) {
    navigation<PlanEditorGraph>(startDestination = PlanEditorRoute) {
        planEditorScreen(
            navController = navController,
            onBack = { navController.popBackStack() },
            onEditName = { planId -> navController.navigate(PlanFormRoute(planId)) },
            onEditWeeksDuration = { planId -> navController.navigate(DurationFormRoute(planId)) },
            onAddTraining = { planId -> navController.navigate(TrainingFormRoute(planId)) },
            onEditTraining = { planId, trainingId ->
                navController.navigate(TrainingFormRoute(planId, trainingId))
            },
            onAddExercise = { planId, trainingId ->
                navController.navigate(ExerciseFormRoute(planId, trainingId))
            },
            onEditExercise = { planId, trainingId, exerciseId ->
                navController.navigate(ExerciseFormRoute(planId, trainingId, exerciseId))
            }
        )
        planFormDialog(
            onDismiss = { navController.navigateUp() }
        )
        durationFormDialog(
            onDismiss = { navController.navigateUp() }
        )
        trainingFormDialog(
            onDismiss = { navController.navigateUp() }
        )
        exerciseFormDialog(
            onDismiss = { navController.navigateUp() }
        )
    }
}
