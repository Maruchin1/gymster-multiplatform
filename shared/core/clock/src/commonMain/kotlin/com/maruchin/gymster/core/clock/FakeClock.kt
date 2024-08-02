package com.maruchin.gymster.core.clock

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant

class FakeClock : Clock {

    private var now: Instant = Clock.System.now()

    override fun now(): Instant = now

    fun setNow(dateTime: LocalDateTime) {
        val timeZone = TimeZone.currentSystemDefault()
        now = dateTime.toInstant(timeZone)
    }

    fun setNow(date: LocalDate) {
        val timeZone = TimeZone.currentSystemDefault()
        now = date.atTime(hour = 12, minute = 0).toInstant(timeZone)
    }
}
