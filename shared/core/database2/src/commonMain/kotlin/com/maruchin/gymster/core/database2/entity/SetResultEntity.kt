package com.maruchin.gymster.core.database2.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SetResultEntity(
    @PrimaryKey val id: String,
    val exerciseId: String,
    val type: String,
    val weight: Double?,
    val reps: Int?
)
