package com.maruchin.gymster.core.database2.dao

import com.maruchin.gymster.core.database2.entity.ExerciseEntity
import com.maruchin.gymster.core.database2.room.FakeGymsterDatabase
import kotlinx.coroutines.flow.update

class FakeExerciseDao internal constructor(private val database: FakeGymsterDatabase) :
    ExerciseDao {

    override suspend fun insertExercises(entities: List<ExerciseEntity>) {
        database.exercises.update { it + entities.associateBy(ExerciseEntity::id) }
    }
}
