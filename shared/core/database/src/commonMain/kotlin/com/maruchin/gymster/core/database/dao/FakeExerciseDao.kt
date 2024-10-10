package com.maruchin.gymster.core.database.dao

import com.maruchin.gymster.core.database.entity.ExerciseEntity
import com.maruchin.gymster.core.database.room.FakeGymsterDatabase
import kotlinx.coroutines.flow.update

class FakeExerciseDao internal constructor(private val database: FakeGymsterDatabase) :
    ExerciseDao {

    override suspend fun insertExercises(entities: List<ExerciseEntity>) {
        database.exercises.update { it + entities.associateBy(ExerciseEntity::id) }
    }
}
