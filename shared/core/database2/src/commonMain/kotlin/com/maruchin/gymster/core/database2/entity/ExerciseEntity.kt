package com.maruchin.gymster.core.database2.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val trainingId: Long,
    val name: String,
    val regularSets: Int,
    val dropSets: Int,
    val minReps: Int,
    val maxReps: Int
)
