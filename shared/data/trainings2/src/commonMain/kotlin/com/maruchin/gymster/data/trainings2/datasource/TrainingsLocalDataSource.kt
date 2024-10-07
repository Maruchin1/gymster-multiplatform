package com.maruchin.gymster.data.trainings2.datasource

import com.maruchin.gymster.core.database2.dao.ExerciseDao
import com.maruchin.gymster.core.database2.dao.SetResultDao
import com.maruchin.gymster.core.database2.dao.TrainingDao
import com.maruchin.gymster.core.database2.relation.TrainingWithExercises
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

internal class TrainingsLocalDataSource(
    private val trainingDao: TrainingDao,
    private val exerciseDao: ExerciseDao,
    private val setResultDao: SetResultDao
) {

    fun observeAllTrainings(): Flow<List<TrainingWithExercises>> =
        trainingDao.observeAllTrainingsWithExercises()

    fun observeTraining(id: String): Flow<TrainingWithExercises?> =
        trainingDao.observeTrainingWithExercises(id)

    suspend fun addTraining(trainingWithExercises: TrainingWithExercises) {
        val training = trainingWithExercises.training
        val exercises = trainingWithExercises.exercises.map { it.exercise }
        val setResults = trainingWithExercises.exercises.flatMap { it.results }
        setResultDao.insertSetResults(setResults)
        exerciseDao.insertExercises(exercises)
        trainingDao.insertTraining(training)
    }

    suspend fun updateTraining(trainingId: String, date: LocalDate) {
        val training = trainingDao.getTraining(trainingId)
        checkNotNull(training)
        val updatedTraining = training.copy(date = date)
        trainingDao.updateTraining(updatedTraining)
    }

    suspend fun deleteTraining(trainingId: String) {
        val training = trainingDao.getTraining(trainingId)
        checkNotNull(training)
        trainingDao.deleteTraining(training)
    }

    suspend fun updateSetResultWeight(setResultId: String, weight: Double?) {
        val setResult = setResultDao.getSetResult(setResultId)
        checkNotNull(setResult)
        val updatedSetResult = setResult.copy(weight = weight)
        setResultDao.updateSetResult(updatedSetResult)
    }

    suspend fun updateSetResultReps(setResultId: String, reps: Int?) {
        val setResult = setResultDao.getSetResult(setResultId)
        checkNotNull(setResult)
        val updatedSetResult = setResult.copy(reps = reps)
        setResultDao.updateSetResult(updatedSetResult)
    }
}
