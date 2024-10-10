package com.maruchin.gymster.core.database.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

internal class LocalDateConverter {

    @TypeConverter
    fun localDateToString(date: LocalDate): String = date.toString()

    @TypeConverter
    fun stringToLocalDate(string: String): LocalDate = LocalDate.parse(string)
}
