package com.maruchin.gymster.android.plans

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.maruchin.gymster.android.plans.dayform.DayFormRoute
import com.maruchin.gymster.android.plans.dayform.dayFormDialog
import com.maruchin.gymster.android.plans.exerciseform.ExerciseFormRoute
import com.maruchin.gymster.android.plans.exerciseform.exerciseFormDialog
import com.maruchin.gymster.android.plans.planeditor.PlanEditorRoute
import com.maruchin.gymster.android.plans.planeditor.planEditorScreen
import com.maruchin.gymster.android.plans.planform.PlanFormRoute
import com.maruchin.gymster.android.plans.planform.planFormDialog
import com.maruchin.gymster.android.plans.planlist.PlanListRoute
import com.maruchin.gymster.android.plans.planlist.planListScreen
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
