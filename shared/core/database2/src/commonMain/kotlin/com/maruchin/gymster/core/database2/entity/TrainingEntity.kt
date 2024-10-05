package com.maruchin.gymster.core.database2.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrainingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val trainingWeekId: Long,
    val name: String,
    val planName: String
)
