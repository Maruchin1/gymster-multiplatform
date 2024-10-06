package com.maruchin.gymster.core.database2.dao

import com.maruchin.gymster.core.database2.entity.PlannedTrainingEntity
import com.maruchin.gymster.core.database2.relation.PlannedTrainingWithPlannedExercises
import com.maruchin.gymster.core.database2.room.FakeGymsterDatabase
import kotlinx.coroutines.flow.update

class FakePlannedTrainingDao(private val database: FakeGymsterDatabase) : PlannedTrainingDao {

    override suspend fun getPlannedTrainingWithPlannedExercises(
        id: Long
    ): PlannedTrainingWithPlannedExercises? {
        val trainings = database.plannedTrainings.value
        val exercises = database.plannedExercises.value

        return trainings[id]?.let { training ->
            PlannedTrainingWithPlannedExercises(
                training = training,
                exercises = exercises.values.filter { it.trainingId == training.id }
            )
        }
    }

    override suspend fun getPlannedTraining(id: Long): PlannedTrainingEntity? =
        database.plannedTrainings.value[id]

    override suspend fun insertPlannedTraining(entity: PlannedTrainingEntity): Long {
        val newId = (database.plannedTrainings.value.keys.maxOfOrNull { it } ?: 0) + 1
        database.plannedTrainings.update { trainings ->
            val newEntity = entity.copy(id = newId)
            trainings + (newId to newEntity)
        }
        return newId
    }

    override suspend fun updatePlannedTraining(entity: PlannedTrainingEntity) {
        database.plannedTrainings.update { trainings ->
            trainings + (entity.id to entity)
        }
    }

    override suspend fun deletePlannedTraining(entity: PlannedTrainingEntity) {
        database.plannedTrainings.update { trainings ->
            trainings - entity.id
        }
    }
}
