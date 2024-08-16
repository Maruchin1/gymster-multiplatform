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

    fun getWeek(weekNumber: Int) = weeks.first { it.number == weekNumber }

    fun getCurrentWeek(): TrainingWeek = weeks.firstOrNull { !it.isComplete } ?: weeks.last()

    companion object {

        internal fun from(plan: Plan, startDate: LocalDate) = TrainingBlock(
            id = "",
            planName = plan.name,
            startDate = startDate,
            weeks = List(plan.weeksDuration) { TrainingWeek.from(plan, it) }
        )
    }
}
