package com.maruchin.gymster.feature.plans.planlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.plans.repository.PlansRepository2
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlanListViewModel :
    ViewModel(),
    KoinComponent {

    private val plansRepository: PlansRepository2 by inject()

    val uiState: StateFlow<PlanListUiState> = plansRepository.observeAllPlans()
        .map { PlanListUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = PlanListUiState()
        )

    fun createPlan(name: String) = viewModelScope.launch {
        plansRepository.createPlan(name)
    }

    fun seedPlans() = viewModelScope.launch {
        samplePlans.first().let { plan ->
            val planId = plansRepository.createPlan(plan.name)
            plan.trainings.forEach { training ->
                val trainingId = plansRepository.addTraining(
                    planId = planId,
                    name = training.name
                )
                training.exercises.forEach { exercise ->
                    plansRepository.addExercise(
                        trainingId = trainingId,
                        name = exercise.name,
                        sets = exercise.sets,
                        reps = exercise.reps
                    )
                }
            }
        }
    }
}
