package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.Plan
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

data class TrainingBlock(
    val id: String,
    val planName: String,
    val startDate: LocalDate,
    val weeks: List<TrainingWeek>
) {

    val endDate: LocalDate
        get() = startDate.plus(DatePeriod(days = weeks.size * 7))

    val currentWeekIndex: Int
        get() = weeks.indexOfFirst { !it.isComplete }

    fun getWeek(index: Int) = weeks[index]

    companion object {

        internal fun from(plan: Plan, startDate: LocalDate) = TrainingBlock(
            id = "",
            planName = plan.name,
            startDate = startDate,
            weeks = List(plan.weeksDuration) { TrainingWeek.from(plan) }
        )
    }
}
