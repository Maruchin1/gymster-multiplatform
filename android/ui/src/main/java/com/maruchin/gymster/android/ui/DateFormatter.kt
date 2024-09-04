package com.maruchin.gymster.android.ui

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun LocalDate.formatMedium(): String = format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
