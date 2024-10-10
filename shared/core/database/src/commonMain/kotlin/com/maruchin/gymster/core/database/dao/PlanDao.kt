package com.maruchin.gymster.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.maruchin.gymster.core.database.entity.PlanEntity
import com.maruchin.gymster.core.database.relation.PlanWithPlannedTrainings
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanDao {

    @Transaction
    @Query("SELECT * FROM PlanEntity")
    fun observeAllPlansWithPlannedTrainings(): Flow<List<PlanWithPlannedTrainings>>

    @Transaction
    @Query("SELECT * FROM PlanEntity WHERE id = :id")
    fun observePlanWithPlannedTrainings(id: Long): Flow<PlanWithPlannedTrainings?>

    @Transaction
    @Query("SELECT * FROM PlanEntity WHERE id = :id")
    suspend fun getPlanWithPlannedTraining(id: Long): PlanWithPlannedTrainings?

    @Query("SELECT * FROM PlanEntity WHERE id = :id")
    suspend fun getPlan(id: Long): PlanEntity?

    @Insert
    suspend fun insertPlan(entity: PlanEntity): Long

    @Update
    suspend fun updatePlan(entity: PlanEntity)

    @Delete
    suspend fun deletePlan(entity: PlanEntity)
}
