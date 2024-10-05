package com.maruchin.gymster.core.database2.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SetResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val exerciseId: Long,
    val type: String,
    val weight: Double?,
    val reps: Int?
)
