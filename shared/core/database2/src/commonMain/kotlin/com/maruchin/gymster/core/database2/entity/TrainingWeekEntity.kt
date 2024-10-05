package com.maruchin.gymster.core.database2.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrainingWeekEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val isComplete: Boolean
)
