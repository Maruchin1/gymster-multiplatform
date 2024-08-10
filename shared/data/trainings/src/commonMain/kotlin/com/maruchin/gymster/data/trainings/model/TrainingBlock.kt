package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.Plan

data class TrainingBlock(val id: String, val planName: String, val weeks: List<TrainingWeek>) {

    fun getWeek(weekNumber: Int) = weeks.first { it.number == weekNumber }

    companion object {

        internal fun from(plan: Plan) = TrainingBlock(
            id = "",
            planName = plan.name,
            weeks = List(plan.weeksDuration) { TrainingWeek.from(plan, it) }
        )
    }
}
