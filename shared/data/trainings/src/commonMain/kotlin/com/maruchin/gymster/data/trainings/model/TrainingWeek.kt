package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.Plan

data class TrainingWeek(val trainings: List<Training>) {

    val isComplete: Boolean
        get() = trainings.all { it.isComplete }

    val notCompleteTrainings: List<Training>
        get() = trainings.filterNot { it.isComplete }

    fun getTraining(trainingId: String) = trainings.first { it.id == trainingId }

    companion object {

        internal fun from(plan: Plan) = TrainingWeek(
            trainings = plan.trainings.map { Training.from(it) }
        )
    }
}
