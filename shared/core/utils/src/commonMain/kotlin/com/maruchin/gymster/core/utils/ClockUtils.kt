package com.maruchin.gymster.core.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

val Clock.currentDateTime: LocalDateTime
    get() = now().toLocalDateTime(TimeZone.currentSystemDefault())

val Clock.currentDate: LocalDate
    get() = currentDateTime.date
