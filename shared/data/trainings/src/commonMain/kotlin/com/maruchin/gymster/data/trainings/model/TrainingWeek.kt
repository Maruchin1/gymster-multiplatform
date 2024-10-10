package com.maruchin.gymster.data.trainings.model

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

data class TrainingWeek(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val trainings: List<Training>
) {

    init {
        require(startDate.dayOfWeek == DayOfWeek.MONDAY) { "Start date must be a Monday" }
        require(endDate.dayOfWeek == DayOfWeek.SUNDAY) { "End date must be a Sunday" }
    }
}

fun List<Training>.groupIntoWeeks(): List<TrainingWeek> {
    val newestMonday = first().date.minus(
        value = first().date.dayOfWeek.ordinal,
        unit = DateTimeUnit.DAY
    )
    val oldestMonday = last().date.minus(
        value = last().date.dayOfWeek.ordinal,
        unit = DateTimeUnit.DAY
    )
    val allMondays = generateSequence(oldestMonday) { it.plus(7, DateTimeUnit.DAY) }
        .takeWhile { it <= newestMonday }
        .toList()
    return allMondays.map { startDate ->
        val endDate = startDate.plus(6, DateTimeUnit.DAY)
        val weekTrainings = filter { it.date in startDate..endDate }
        TrainingWeek(startDate, endDate, weekTrainings)
    }.sortedByDescending { it.startDate }
}
