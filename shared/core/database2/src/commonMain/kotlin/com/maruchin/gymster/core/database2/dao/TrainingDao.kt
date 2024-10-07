package com.maruchin.gymster.core.database2.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.maruchin.gymster.core.database2.entity.TrainingEntity
import com.maruchin.gymster.core.database2.relation.TrainingWithExercises
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {

    @Query("SELECT * FROM TrainingEntity")
    fun observeAllTrainingsWithExercises(): Flow<List<TrainingWithExercises>>

    @Query("SELECT * FROM TrainingEntity WHERE id = :id")
    fun observeTrainingWithExercises(id: String): Flow<TrainingWithExercises?>

    @Query("SELECT * FROM TrainingEntity WHERE id = :id")
    suspend fun getTraining(id: String): TrainingEntity?

    @Insert
    suspend fun insertTraining(entity: TrainingEntity)

    @Update
    suspend fun updateTraining(entity: TrainingEntity)

    @Delete
    suspend fun deleteTraining(entity: TrainingEntity)
}
