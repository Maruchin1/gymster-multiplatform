package com.maruchin.gymster.android.planlist.newplan

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import com.maruchin.gymster.planlist.newplan.NewPlanViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object NewPlanRoute

internal fun NavGraphBuilder.newPlanDialog(onDismiss: () -> Unit) {
    dialog<NewPlanRoute> {
        val viewModel = viewModel { NewPlanViewModel.create() }

        NewPlanDialog(
            onDismiss = onDismiss,
            onConfirm = { viewModel.createPlan(it).invokeOnCompletion { onDismiss() } }
        )
    }
}
