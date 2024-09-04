package com.maruchin.gymster.android.trainingeditor

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.maruchin.gymster.android.trainingeditor.editor.TrainingEditorRoute
import com.maruchin.gymster.android.trainingeditor.editor.trainingEditorScreen
import kotlinx.serialization.Serializable

@Serializable
data class TrainingEditorGraph(val trainingBlockId: String, val trainingId: String)

fun NavGraphBuilder.trainingEditorGraph(navController: NavController) {
    navigation<TrainingEditorGraph>(startDestination = TrainingEditorRoute) {
        trainingEditorScreen(
            navController = navController,
            onBack = { navController.navigateUp() }
        )
    }
}
