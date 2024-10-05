package com.maruchin.gymster.core.database2.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.maruchin.gymster.core.database2.entity.ExerciseEntity

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM ExerciseEntity WHERE id = :id")
    suspend fun getExercise(id: Long): ExerciseEntity?

    @Insert
    suspend fun insertExercises(entities: List<ExerciseEntity>)
}
