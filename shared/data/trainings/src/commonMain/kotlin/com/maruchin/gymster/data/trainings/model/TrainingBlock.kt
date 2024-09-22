package com.maruchin.gymster.data.trainings.model

import com.maruchin.gymster.data.plans.model.Plan
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

data class TrainingBlock(
    val id: String,
    val planName: String,
    val startDate: LocalDate,
    val weeks: List<TrainingWeek>,
    val isActive: Boolean
) {

    val endDate: LocalDate
        get() = startDate.plus(DatePeriod(days = weeks.size * 7))

    val currentWeekIndex: Int
        get() = weeks.indexOfFirst { !it.isComplete }

    fun getTraining(weekIndex: Int, trainingIndex: Int): Training =
        weeks[weekIndex].trainings[trainingIndex]

    fun getPreviousTraining(weekIndex: Int, trainingIndex: Int): Training? =
        weeks.getOrNull(weekIndex - 1)?.trainings?.get(trainingIndex)

    companion object {

        val possibleWeeksDurations = arrayOf(4, 8, 12, 16, 20, 24)

        internal fun from(plan: Plan, startDate: LocalDate, weeksDuration: Int) = TrainingBlock(
            id = "",
            planName = plan.name,
            startDate = startDate,
            weeks = List(weeksDuration) { TrainingWeek.from(plan) },
            isActive = false
        )
    }
}
