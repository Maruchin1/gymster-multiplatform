package com.maruchin.gymster.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.maruchin.gymster.core.database.entity.SetResultEntity

@Dao
interface SetResultDao {

    @Query("SELECT * FROM SetResultEntity WHERE id = :id")
    suspend fun getSetResult(id: String): SetResultEntity?

    @Insert
    suspend fun insertSetResults(entities: List<SetResultEntity>)

    @Update
    suspend fun updateSetResult(entity: SetResultEntity)
}
