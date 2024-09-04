package com.maruchin.gymster.android.planlist

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.maruchin.gymster.android.planlist.newplan.NewPlanRoute
import com.maruchin.gymster.android.planlist.newplan.newPlanDialog
import com.maruchin.gymster.android.planlist.planlist.PlanListRoute
import com.maruchin.gymster.android.planlist.planlist.planListScreen
import com.maruchin.gymster.android.planlist.trainingblockform.TrainingBlockFormRoute
import com.maruchin.gymster.android.planlist.trainingblockform.trainingBlockFormDialog
import kotlinx.serialization.Serializable

@Serializable
data object PlanListGraph

fun NavGraphBuilder.planListGraph(
    navController: NavController,
    onEditPlan: (planId: String) -> Unit
) {
    navigation<PlanListGraph>(startDestination = PlanListRoute) {
        planListScreen(
            onBack = { navController.navigateUp() },
            onAddPlan = { navController.navigate(NewPlanRoute) },
            onStartTrainingBlock = { navController.navigate(TrainingBlockFormRoute(it)) },
            onEditPlan = onEditPlan
        )
        newPlanDialog(
            onDismiss = { navController.navigateUp() }
        )
        trainingBlockFormDialog(
            onDismiss = { navController.navigateUp() }
        )
    }
}
