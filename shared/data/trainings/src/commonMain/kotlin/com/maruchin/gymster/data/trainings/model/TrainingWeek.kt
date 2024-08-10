package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.Plan

data class TrainingWeek(val number: Int, val trainings: List<Training>) {

    fun getTraining(trainingId: String) = trainings.first { it.id == trainingId }

    companion object {

        internal fun from(plan: Plan, weekNumber: Int) = TrainingWeek(
            number = weekNumber,
            trainings = plan.trainings.map { Training.from(it) }
        )
    }
}
