package com.maruchin.gymster.core.database.dao

import com.maruchin.gymster.core.database.entity.SetResultEntity
import com.maruchin.gymster.core.database.room.FakeGymsterDatabase
import kotlinx.coroutines.flow.update

class FakeSetResultDao internal constructor(private val database: FakeGymsterDatabase) :
    SetResultDao {

    override suspend fun getSetResult(id: String): SetResultEntity? = database.setResults.value[id]

    override suspend fun insertSetResults(entities: List<SetResultEntity>) {
        database.setResults.update { it + entities.associateBy(SetResultEntity::id) }
    }

    override suspend fun updateSetResult(entity: SetResultEntity) {
        database.setResults.update { it + (entity.id to entity) }
    }
}
