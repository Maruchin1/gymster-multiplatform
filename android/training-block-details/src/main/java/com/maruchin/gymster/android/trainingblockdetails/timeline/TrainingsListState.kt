package com.maruchin.gymster.android.trainingblockdetails.timeline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.maruchin.gymster.data.trainings.model.Training

@Stable
internal class TrainingsListState(expandedTrainingsIds: List<String>) {

    private var expandedTrainingIds by mutableStateOf(expandedTrainingsIds.toSet())

    fun isExpanded(training: Training) = training.id in expandedTrainingIds

    fun toggleExpanded(training: Training) {
        expandedTrainingIds = if (isExpanded(training)) {
            expandedTrainingIds - training.id
        } else {
            expandedTrainingIds + training.id
        }
    }

    companion object {

        val saver = listSaver(
            save = {
                it.expandedTrainingIds.toList()
            },
            restore = {
                TrainingsListState(it)
            }
        )
    }
}

@Composable
internal fun rememberTrainingsListState(expandedTrainings: List<Training>): TrainingsListState =
    rememberSaveable(expandedTrainings, saver = TrainingsListState.saver) {
        TrainingsListState(expandedTrainings.map { it.id })
    }
