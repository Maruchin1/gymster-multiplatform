package com.maruchin.multiplatform.gymster.android.trainingplans

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.maruchin.multiplatform.gymster.android.trainingplans.dayform.DayFormRoute
import com.maruchin.multiplatform.gymster.android.trainingplans.dayform.dayFormDialog
import com.maruchin.multiplatform.gymster.android.trainingplans.exerciseform.ExerciseFormRoute
import com.maruchin.multiplatform.gymster.android.trainingplans.exerciseform.exerciseFormDialog
import com.maruchin.multiplatform.gymster.android.trainingplans.planeditor.PlanEditorRoute
import com.maruchin.multiplatform.gymster.android.trainingplans.planeditor.planEditorScreen
import com.maruchin.multiplatform.gymster.android.trainingplans.planform.PlanFormRoute
import com.maruchin.multiplatform.gymster.android.trainingplans.planform.planFormDialog
import com.maruchin.multiplatform.gymster.android.trainingplans.planlist.PlanListRoute
import com.maruchin.multiplatform.gymster.android.trainingplans.planlist.planListScreen
import kotlinx.serialization.Serializable

@Serializable
object TrainingPlansGraph

fun NavGraphBuilder.trainingPlansGraph(navController: NavController) {
    navigation<TrainingPlansGraph>(startDestination = PlanListRoute) {
        planListScreen(
            onAddTrainingPlan = {
                navController.navigate(PlanFormRoute(null))
            },
            onEditTrainingPlan = { planId ->
                navController.navigate(PlanEditorRoute(planId))
            }
        )
        planEditorScreen(
            onBack = {
                navController.navigateUp()
            },
            onEditName = { planId ->
                navController.navigate(PlanFormRoute(planId))
            },
            onAddDay = { planId ->
                navController.navigate(DayFormRoute(planId, null))
            },
            onEditDay = { planId, dayId ->
                navController.navigate(DayFormRoute(planId, dayId))
            },
            onAddExercise = { planId, dayId ->
                navController.navigate(ExerciseFormRoute(planId, dayId, null))
            },
            onEditExercise = { planId, dayId, exerciseId ->
                navController.navigate(ExerciseFormRoute(planId, dayId, exerciseId))
            }
        )
        planFormDialog(
            onClose = {
                navController.navigateUp()
            }
        )
        dayFormDialog(
            onClose = {
                navController.navigateUp()
            }
        )
        exerciseFormDialog(
            onClose = {
                navController.navigateUp()
            }
        )
    }
}
