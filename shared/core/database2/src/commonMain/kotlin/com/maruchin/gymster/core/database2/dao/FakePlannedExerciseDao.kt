package com.maruchin.gymster.core.database2.dao

import com.maruchin.gymster.core.database2.entity.PlannedExerciseEntity
import com.maruchin.gymster.core.database2.room.FakeGymsterDatabase
import kotlinx.coroutines.flow.update

class FakePlannedExerciseDao internal constructor(private val database: FakeGymsterDatabase) :
    PlannedExerciseDao {

    override suspend fun getPlannedExercise(id: Long): PlannedExerciseEntity? =
        database.plannedExercises.value[id]

    override suspend fun countPlannedExercisesForTraining(trainingId: Long): Int =
        database.plannedExercises.value.count { (_, exercise) -> exercise.trainingId == trainingId }

    override suspend fun insertPlannedExercise(entity: PlannedExerciseEntity): Long {
        val newId = (database.plannedExercises.value.maxOfOrNull { it.key } ?: 0) + 1
        database.plannedExercises.update { exercises ->
            val newEntity = entity.copy(id = newId)
            exercises + (newId to newEntity)
        }
        return newId
    }

    override suspend fun updatePlannedExercise(entity: PlannedExerciseEntity) {
        database.plannedExercises.update { exercises ->
            exercises + (entity.id to entity)
        }
    }

    override suspend fun deletePlannedExercise(entity: PlannedExerciseEntity) {
        database.plannedExercises.update { exercises ->
            exercises - entity.id
        }
    }
}
