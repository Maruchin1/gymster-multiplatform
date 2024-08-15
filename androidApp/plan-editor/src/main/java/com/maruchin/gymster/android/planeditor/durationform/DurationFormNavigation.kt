package com.maruchin.gymster.android.planeditor.durationform

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import com.maruchin.gymster.feature.planeditor.durationform.DurationFormViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data class DurationFormRoute(val planId: String)

internal fun NavGraphBuilder.durationFormDialog(onDismiss: () -> Unit) {
    dialog<DurationFormRoute> { entry ->
        val (planId) = entry.toRoute<DurationFormRoute>()
        val viewModel = viewModel { DurationFormViewModel.get(planId) }
        val plan by viewModel.plan.collectAsStateWithLifecycle()

        DurationDialog(
            plan = plan,
            onDismiss = onDismiss,
            onSave = { viewModel.saveDuration(it).invokeOnCompletion { onDismiss() } }
        )
    }
}
