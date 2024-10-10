package com.maruchin.gymster.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.maruchin.gymster.core.database.entity.ExerciseEntity

@Dao
interface ExerciseDao {

    @Insert
    suspend fun insertExercises(entities: List<ExerciseEntity>)
}
