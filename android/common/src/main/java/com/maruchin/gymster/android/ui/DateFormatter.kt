package com.maruchin.gymster.android.ui

import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate

fun LocalDate.formatMedium(): String =
    toJavaLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))

fun LocalDate.formatFull(): String =
    toJavaLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
