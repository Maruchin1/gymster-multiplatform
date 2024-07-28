package com.maruchin.multiplatform.gymster.android.trainingplans.dayform

import androidx.compose.runtime.getValue
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.dayform.DayFormViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data class DayFormRoute(val planId: String, val dayId: String?)

internal fun NavGraphBuilder.dayFormDialog(onClose: () -> Unit) {
    dialog<DayFormRoute>(
        dialogProperties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        val (planId, dayId) = it.toRoute<DayFormRoute>()
        val viewModel = viewModel {
            DayFormViewModel.get(planId = planId, dayId = dayId)
        }
        val day by viewModel.day.collectAsStateWithLifecycle()

        DayFormDialog(
            day = day,
            onClose = onClose,
            onSave = { name ->
                viewModel.saveDay(name).invokeOnCompletion { onClose() }
            }
        )
    }
}
