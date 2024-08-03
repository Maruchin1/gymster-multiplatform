package com.maruchin.gymster.android.plans.dayform

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.toRoute
import com.maruchin.gymster.android.ui.fullScreenDialog
import com.maruchin.gymster.feature.plans.dayform.DayFormViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data class DayFormRoute(val planId: String, val dayId: String?)

internal fun NavGraphBuilder.dayFormDialog(onClose: () -> Unit) {
    fullScreenDialog<DayFormRoute> {
        val (planId, dayId) = it.toRoute<DayFormRoute>()
        val viewModel = viewModel {
            DayFormViewModel.get(planId = planId, dayId = dayId)
        }
        val day by viewModel.day.collectAsStateWithLifecycle()

        DayFormDialog(
            plannedTraining = day,
            onClose = onClose,
            onSave = { name ->
                viewModel.saveDay(name).invokeOnCompletion { onClose() }
            }
        )
    }
}
