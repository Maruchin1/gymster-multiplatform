package com.maruchin.gymster.android.planeditor.trainingform

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import com.maruchin.gymster.feature.planeditor.trainingform.TrainingFormViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data class TrainingFormRoute(val planId: String, val trainingId: String? = null)

internal fun NavGraphBuilder.trainingFormDialog(onDismiss: () -> Unit) {
    dialog<TrainingFormRoute> { entry ->
        val (planId, trainingId) = entry.toRoute<TrainingFormRoute>()
        val viewModel = viewModel { TrainingFormViewModel.get(planId, trainingId) }
        val training by viewModel.training.collectAsStateWithLifecycle()

        TrainingFormDialog(
            plannedTraining = training,
            onDismiss = onDismiss,
            onSave = { viewModel.saveTraining(it).invokeOnCompletion { onDismiss() } }
        )
    }
}
