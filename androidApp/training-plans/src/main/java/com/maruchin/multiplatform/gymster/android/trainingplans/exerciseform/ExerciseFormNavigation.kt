package com.maruchin.multiplatform.gymster.android.trainingplans.exerciseform

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.toRoute
import com.maruchin.gymster.android.ui.fullScreenDialog
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.exerciseform.ExerciseFormViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data class ExerciseFormRoute(
    val planId: String,
    val dayId: String,
    val exerciseId: String?
)

internal fun NavGraphBuilder.exerciseFormDialog(onClose: () -> Unit) {
    fullScreenDialog<ExerciseFormRoute> {
        val (planId, dayId, exerciseId) = it.toRoute<ExerciseFormRoute>()
        val viewModel = viewModel {
            ExerciseFormViewModel.get(
                planId = planId,
                dayId = dayId,
                exerciseId = exerciseId
            )
        }
        val exercise by viewModel.exercise.collectAsStateWithLifecycle()

        ExerciseFormDialog(
            exercise = exercise,
            onClose = onClose,
            onSave = { name, sets, reps ->
                viewModel.saveExercise(name, sets, reps).invokeOnCompletion { onClose() }
            }
        )
    }
}
