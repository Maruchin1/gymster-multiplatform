package com.maruchin.gymster.android.plans.planform

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import com.maruchin.gymster.feature.plans.planform.PlanFormViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data class PlanFormRoute(val planId: String?)

internal fun NavGraphBuilder.planFormDialog(onClose: () -> Unit) {
    dialog<PlanFormRoute> {
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
