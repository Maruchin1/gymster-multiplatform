package com.maruchin.gymster.core.database.dao

import com.maruchin.gymster.core.database.entity.PlanEntity
import com.maruchin.gymster.core.database.relation.PlanWithPlannedTrainings
import com.maruchin.gymster.core.database.relation.PlannedTrainingWithPlannedExercises
import com.maruchin.gymster.core.database.room.FakeGymsterDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class FakePlanDao internal constructor(private val database: FakeGymsterDatabase) : PlanDao {

    override fun observeAllPlansWithPlannedTrainings(): Flow<List<PlanWithPlannedTrainings>> =
        combine(
            database.plans,
            database.plannedTrainings,
            database.plannedExercises
        ) { plans, trainings, exercises ->
            plans.values.map { plan ->
                PlanWithPlannedTrainings(
                    plan = plan,
                    trainings = trainings.values.filter { it.planId == plan.id }
                        .map { matchingTraining ->
                            PlannedTrainingWithPlannedExercises(
                                training = matchingTraining,
                                exercises = exercises.values.filter {
                                    it.trainingId == matchingTraining.id
                                }
                            )
                        }
                )
            }
        }

    override fun observePlanWithPlannedTrainings(id: Long): Flow<PlanWithPlannedTrainings?> =
        combine(
            database.plans,
            database.plannedTrainings,
            database.plannedExercises
        ) { plans, trainings, exercises ->
            plans[id]?.let { plan ->
                PlanWithPlannedTrainings(
                    plan = plan,
                    trainings = trainings.values.filter { it.planId == plan.id }
                        .map { matchingTraining ->
                            PlannedTrainingWithPlannedExercises(
                                training = matchingTraining,
                                exercises = exercises.values.filter {
                                    it.trainingId == matchingTraining.id
                                }
                            )
                        }
                )
            }
        }

    override suspend fun getPlanWithPlannedTraining(id: Long): PlanWithPlannedTrainings? {
        val plans = database.plans.value
        val trainings = database.plannedTrainings.value
        val exercises = database.plannedExercises.value

        return plans[id]?.let { plan ->
            PlanWithPlannedTrainings(
                plan = plan,
                trainings = trainings.values.filter { it.planId == plan.id }
                    .map { matchingTraining ->
                        PlannedTrainingWithPlannedExercises(
                            training = matchingTraining,
                            exercises = exercises.values.filter {
                                it.trainingId ==
                                    matchingTraining.id
                            }
                        )
                    }
            )
        }
    }

    override suspend fun getPlan(id: Long): PlanEntity? = database.plans.value[id]

    override suspend fun insertPlan(entity: PlanEntity): Long {
        val newId = (database.plans.value.keys.maxOrNull() ?: 0) + 1
        database.plans.update { plans ->
            val newEntity = entity.copy(id = newId)
            plans + (newId to newEntity)
        }
        return newId
    }

    override suspend fun updatePlan(entity: PlanEntity) {
        database.plans.update { plans ->
            plans + (entity.id to entity)
        }
    }

    override suspend fun deletePlan(entity: PlanEntity) {
        database.plans.update { plans ->
            plans - entity.id
        }
    }
}
