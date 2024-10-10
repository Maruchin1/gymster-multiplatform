package com.maruchin.gymster.core.database.dao

import com.maruchin.gymster.core.database.entity.TrainingEntity
import com.maruchin.gymster.core.database.relation.ExerciseWithSetResults
import com.maruchin.gymster.core.database.relation.TrainingWithExercises
import com.maruchin.gymster.core.database.room.FakeGymsterDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class FakeTrainingDao internal constructor(private val database: FakeGymsterDatabase) :
    TrainingDao {

    override fun observeAllTrainingsWithExercises(): Flow<List<TrainingWithExercises>> = combine(
        database.trainings,
        database.exercises,
        database.setResults
    ) { trainings, exercises, setResults ->
        trainings.values.map { training ->
            TrainingWithExercises(
                training = training,
                exercises = exercises.values.filter { it.trainingId == training.id }
                    .map { exercise ->
                        ExerciseWithSetResults(
                            exercise = exercise,
                            results = setResults.values.filter { it.exerciseId == exercise.id }
                        )
                    }
            )
        }
    }

    override fun observeTrainingWithExercises(id: String): Flow<TrainingWithExercises?> = combine(
        database.trainings,
        database.exercises,
        database.setResults
    ) { trainings, exercises, setResults ->
        trainings[id]?.let { training ->
            TrainingWithExercises(
                training = training,
                exercises = exercises.values.filter { it.trainingId == training.id }
                    .map { exercise ->
                        ExerciseWithSetResults(
                            exercise = exercise,
                            results = setResults.values.filter { it.exerciseId == exercise.id }
                        )
                    }
            )
        }
    }

    override suspend fun getTraining(id: String): TrainingEntity? = database.trainings.value[id]

    override suspend fun insertTraining(entity: TrainingEntity) {
        database.trainings.update { it + (entity.id to entity) }
    }

    override suspend fun updateTraining(entity: TrainingEntity) {
        database.trainings.update { it + (entity.id to entity) }
    }

    override suspend fun deleteTraining(entity: TrainingEntity) {
        database.trainings.update { it - entity.id }
    }
}
