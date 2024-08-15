package com.maruchin.gymster.android.planlist.trainingblockform

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import com.maruchin.gymster.planlist.trainingblockform.TrainingBlockFormViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data class TrainingBlockFormRoute(val planId: String)

internal fun NavGraphBuilder.trainingBlockFormDialog(onDismiss: () -> Unit) {
    dialog<TrainingBlockFormRoute> { entry ->
        val (planId) = entry.toRoute<TrainingBlockFormRoute>()
        val viewModel = viewModel { TrainingBlockFormViewModel.get(planId) }

        TrainingBlockFormDialog(
            onDismiss = onDismiss,
            onCreateTrainingBlock = {
                viewModel.createTrainingBlock(it).invokeOnCompletion { onDismiss() }
            }
        )
    }
}
