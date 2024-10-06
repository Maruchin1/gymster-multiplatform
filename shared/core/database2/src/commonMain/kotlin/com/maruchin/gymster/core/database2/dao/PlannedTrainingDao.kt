package com.maruchin.gymster.core.database2.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.maruchin.gymster.core.database2.entity.PlannedTrainingEntity
import com.maruchin.gymster.core.database2.relation.PlannedTrainingWithPlannedExercises

@Dao
interface PlannedTrainingDao {

    @Transaction
    @Query("SELECT * FROM PlannedTrainingEntity WHERE id = :id")
    suspend fun getPlannedTrainingWithPlannedExercises(
        id: Long
    ): PlannedTrainingWithPlannedExercises?

    @Query("SELECT * FROM PlannedTrainingEntity WHERE id = :id")
    suspend fun getPlannedTraining(id: Long): PlannedTrainingEntity?

    @Insert
    suspend fun insertPlannedTraining(entity: PlannedTrainingEntity): Long

    @Update
    suspend fun updatePlannedTraining(entity: PlannedTrainingEntity)

    @Delete
    suspend fun deletePlannedTraining(entity: PlannedTrainingEntity)
}
