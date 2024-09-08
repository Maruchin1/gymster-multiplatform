package com.maruchin.gymster.android.planeditor

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.maruchin.gymster.android.planeditor.planeditor.PlanEditorRoute
import com.maruchin.gymster.android.planeditor.planeditor.planEditorScreen
import kotlinx.serialization.Serializable

@Serializable
data class PlanEditorGraph(val planId: String)

fun NavGraphBuilder.planEditorGraph(navController: NavController) {
    navigation<PlanEditorGraph>(startDestination = PlanEditorRoute) {
        planEditorScreen(
            navController = navController,
            onBack = { navController.popBackStack() }
        )
    }
}
