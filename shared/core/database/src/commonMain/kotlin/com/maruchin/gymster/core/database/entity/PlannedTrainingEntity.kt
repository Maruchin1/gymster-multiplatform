package com.maruchin.gymster.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlannedTrainingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val planId: Long,
    val name: String
)
