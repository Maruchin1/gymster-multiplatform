package com.maruchin.gymster.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity
data class TrainingEntity(
    @PrimaryKey val id: String,
    val name: String,
    val planName: String,
    val date: LocalDate,
    val isComplete: Boolean
)
