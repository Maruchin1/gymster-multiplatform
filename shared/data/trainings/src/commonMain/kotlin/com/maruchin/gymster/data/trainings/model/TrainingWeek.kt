package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.PlannedWeek

data class TrainingWeek(val trainings: List<Training>) {

    val isComplete: Boolean
        get() = trainings.all { it.isComplete }

    val notCompleteTrainings: List<Training>
        get() = trainings.filterNot { it.isComplete }

    companion object {

        internal fun from(plannedWeek: PlannedWeek) = TrainingWeek(
            trainings = plannedWeek.trainings.map { Training.from(it) }
        )
    }
}
