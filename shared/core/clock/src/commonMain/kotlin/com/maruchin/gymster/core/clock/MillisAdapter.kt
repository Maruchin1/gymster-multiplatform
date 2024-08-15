package com.maruchin.gymster.core.clock

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.toMillis(): Long {
    val timeZone = TimeZone.currentSystemDefault()
    val instant = this.toInstant(timeZone)
    return instant.toEpochMilliseconds()
}

fun LocalDate.toMillis(): Long {
    val dateTime = this.atTime(12, 0)
    return dateTime.toMillis()
}

fun Long.toLocalDateTime(): LocalDateTime {
    val instant = Instant.fromEpochMilliseconds(epochMilliseconds = this)
    val timeZone = TimeZone.currentSystemDefault()
    return instant.toLocalDateTime(timeZone)
}

fun Long.toLocalDate(): LocalDate {
    val timeZone = TimeZone.currentSystemDefault()
    val localDateTime = this.toLocalDateTime()
    return localDateTime.date
}
