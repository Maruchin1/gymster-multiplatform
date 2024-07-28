package com.maruchin.multiplatform.gymster.android.trainingplans.planform

import androidx.compose.runtime.getValue
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.planform.PlanFormViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data class PlanFormRoute(val planId: String?)

internal fun NavGraphBuilder.planFormDialog(onClose: () -> Unit) {
    dialog<PlanFormRoute>(
        dialogProperties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        val (planId) = it.toRoute<PlanFormRoute>()
        val viewModel = viewModel { PlanFormViewModel.get(planId) }
        val plan by viewModel.plan.collectAsStateWithLifecycle()

        PlanFormDialog(
            plan = plan,
            onClose = onClose,
            onSave = { name ->
                viewModel.savePlan(name = name).invokeOnCompletion { onClose() }
            }
        )
    }
}
