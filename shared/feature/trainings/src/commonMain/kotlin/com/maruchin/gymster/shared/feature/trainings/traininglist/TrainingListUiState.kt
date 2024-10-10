package com.maruchin.gymster.shared.feature.trainings.traininglist

import com.maruchin.gymster.data.trainings.model.TrainingWeek

data class TrainingListUiState(val trainings: List<TrainingWeek> = emptyList())
