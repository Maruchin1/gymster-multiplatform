package com.maruchin.gymster.core.database2.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.maruchin.gymster.core.database2.entity.PlannedExerciseEntity

@Dao
interface PlannedExerciseDao {

    @Query("SELECT * FROM PlannedExerciseEntity WHERE id = :id")
    suspend fun getPlannedExercise(id: Long): PlannedExerciseEntity?

    @Query("SELECT COUNT(*) FROM PlannedExerciseEntity WHERE trainingId = :trainingId")
    suspend fun countPlannedExercisesForTraining(trainingId: Long): Int

    @Insert
    suspend fun insertPlannedExercise(entity: PlannedExerciseEntity)

    @Update
    suspend fun updatePlannedExercise(entity: PlannedExerciseEntity)

    @Delete
    suspend fun deletePlannedExercise(entity: PlannedExerciseEntity)
}
