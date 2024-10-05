package com.maruchin.gymster.core.database2.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.maruchin.gymster.core.database2.entity.TrainingEntity
import com.maruchin.gymster.core.database2.relation.TrainingWithExercises
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {

    @Query("SELECT * FROM TrainingEntity WHERE id = :id")
    fun observeTrainingWithExercises(id: Long): Flow<TrainingWithExercises?>

    @Query("SELECT * FROM TrainingEntity WHERE id = :id")
    suspend fun getTraining(id: Long): TrainingEntity?

    @Insert
    suspend fun insertTrainings(entities: List<TrainingEntity>)
}
