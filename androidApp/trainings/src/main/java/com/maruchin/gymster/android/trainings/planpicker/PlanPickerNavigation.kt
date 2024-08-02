package com.maruchin.gymster.android.trainings.planpicker

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import com.maruchin.gymster.android.ui.fullScreenDialog
import com.maruchin.gymster.feature.trainings.planpicker.PlanPickerViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object PlanPickerRoute

internal fun NavGraphBuilder.planPickerDialog(onClose: () -> Unit) {
    fullScreenDialog<PlanPickerRoute> {
        val viewModel = viewModel { PlanPickerViewModel.get() }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        PlanPickerDialog(
            state = state,
            onClose = onClose,
            onSelectPlan = viewModel::selectPlan,
            onSelectDay = viewModel::selectDay,
            onSelectDate = viewModel::selectDate,
            onStartTraining = { viewModel.startTraining().invokeOnCompletion { onClose() } }
        )
    }
}
