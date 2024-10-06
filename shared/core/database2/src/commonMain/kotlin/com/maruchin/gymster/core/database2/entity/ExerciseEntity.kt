package com.maruchin.gymster.core.database2.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseEntity(
    @PrimaryKey val id: String,
    val trainingId: String,
    val name: String,
    val regularSets: Int,
    val dropSets: Int,
    val minReps: Int,
    val maxReps: Int
)
