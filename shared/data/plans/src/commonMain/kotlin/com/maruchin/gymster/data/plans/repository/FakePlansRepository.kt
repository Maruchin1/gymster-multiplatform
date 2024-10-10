package com.maruchin.gymster.data.plans.repository

import com.maruchin.gymster.core.utils.updated
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.model.PlannedExercise
import com.maruchin.gymster.data.plans.model.PlannedTraining
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakePlansRepository : PlansRepository {

    private val state = MutableStateFlow(emptyList<Plan>())

    fun setPlans(plans: List<Plan>) {
        state.value = plans
    }

    override fun observeAllPlans(): Flow<List<Plan>> = state

    override fun observePlan(planId: String): Flow<Plan?> = state.map { plans ->
        plans.find { it.id == planId }
    }

    override suspend fun createPlan(name: String): String {
        val newPlan = Plan(
            id = Uuid.random().toString(),
            name = name,
            trainings = emptyList()
        )
        state.update { it + newPlan }
        return newPlan.id
    }

    override suspend fun updatePlan(planId: String, name: String) {
        state.update { plans ->
            plans.updated({ it.id == planId }) { plan ->
                plan.copy(name = name)
            }
        }
    }

    override suspend fun deletePlan(planId: String) {
        state.update { plans ->
            plans.filter { it.id != planId }
        }
    }

    override suspend fun addTraining(planId: String, name: String): String {
        val newTraining = PlannedTraining(
            id = Uuid.random().toString(),
            name = name,
            exercises = emptyList()
        )
        state.update { plans ->
            plans.updated({ it.id == planId }) { plan ->
                plan.copy(trainings = plan.trainings + newTraining)
            }
        }
        return newTraining.id
    }

    override suspend fun updateTraining(trainingId: String, name: String) {
        state.update { plans ->
            plans.updated({ plan -> plan.trainings.any { it.id == trainingId } }) { plan ->
                plan.copy(
                    trainings = plan.trainings.updated({ it.id == trainingId }) { training ->
                        training.copy(name = name)
                    }
                )
            }
        }
    }

    override suspend fun deleteTraining(trainingId: String) {
        state.update { plans ->
            plans.updated({ plan -> plan.trainings.any { it.id == trainingId } }) { plan ->
                plan.copy(
                    trainings = plan.trainings.filter { it.id != trainingId }
                )
            }
        }
    }

    override suspend fun addExercise(
        trainingId: String,
        name: String,
        sets: Sets,
        reps: Reps
    ): String {
        val newExercise = PlannedExercise(
            id = Uuid.random().toString(),
            name = name,
            sets = sets,
            reps = reps
        )
        state.update { plans ->
            plans.updated({ plan -> plan.trainings.any { it.id == trainingId } }) { plan ->
                plan.copy(
                    trainings = plan.trainings.updated({ it.id == trainingId }) { training ->
                        training.copy(exercises = training.exercises + newExercise)
                    }
                )
            }
        }
        return newExercise.id
    }

    override suspend fun updateExercise(exerciseId: String, name: String, sets: Sets, reps: Reps) {
        state.update { plans ->
            plans.updated({ plan ->
                plan.trainings.any { training ->
                    training.exercises.any { it.id == exerciseId }
                }
            }) { plan ->
                plan.copy(
                    trainings = plan.trainings.updated({ training ->
                        training.exercises.any { it.id == exerciseId }
                    }) { training ->
                        training.copy(
                            exercises = training.exercises.updated(
                                { it.id == exerciseId }
                            ) { exercise ->
                                exercise.copy(
                                    name = name,
                                    sets = sets,
                                    reps = reps
                                )
                            }
                        )
                    }
                )
            }
        }
    }

    override suspend fun deleteExercise(exerciseId: String) {
        state.update { plans ->
            plans.updated({ plan ->
                plan.trainings.any { training ->
                    training.exercises.any { it.id == exerciseId }
                }
            }) { plan ->
                plan.copy(
                    trainings = plan.trainings.updated({ training ->
                        training.exercises.any { it.id == exerciseId }
                    }) { training ->
                        training.copy(
                            exercises = training.exercises.filter { it.id != exerciseId }
                        )
                    }
                )
            }
        }
    }

    override suspend fun reorderExercises(exercisesIds: List<String>) {
        state.update { plans ->
            plans.updated({ plan ->
                plan.trainings.any { training ->
                    training.exercises.any { it.id == exercisesIds.first() }
                }
            }) { plan ->
                plan.copy(
                    trainings = plan.trainings.updated({ training ->
                        training.exercises.any { it.id == exercisesIds.first() }
                    }) { training ->
                        training.copy(
                            exercises = training.exercises.sortedBy { exercisesIds.indexOf(it.id) }
                        )
                    }
                )
            }
        }
    }
}
