package com.maruchin.gymster.core.database2.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.maruchin.gymster.core.database2.entity.TrainingWeekEntity
import com.maruchin.gymster.core.database2.relation.TrainingWeekWithTrainings
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingWeekDao {

    @Transaction
    @Query("SELECT * FROM TrainingWeekEntity")
    fun observeAllTrainingWeeksWithTrainings(): Flow<List<TrainingWeekWithTrainings>>

    @Query("SELECT * FROM TrainingWeekEntity WHERE id = :id")
    suspend fun getTrainingWeek(id: Long): TrainingWeekEntity?

    @Insert
    suspend fun insertTrainingWeeks(trainingWeeks: List<TrainingWeekEntity>)

    @Update
    suspend fun updateTrainingWeek(trainingWeek: TrainingWeekEntity)
}
