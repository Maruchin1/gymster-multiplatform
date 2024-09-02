package com.maruchin.gymster.planlist.planlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.plans.repository.PlansRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.get

class PlanListViewModel internal constructor(private val plansRepository: PlansRepository) :
    ViewModel() {

    val uiState: StateFlow<PlanListUiState> = plansRepository.observeAllPlans()
        .map { PlanListUiState.from(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = PlanListUiState.Loading
        )

    fun seedPlans() = viewModelScope.launch {
        samplePlans.first().let { plan ->
            val createdPlan = plansRepository.createPlan(plan.name)
            plan.trainings.forEach { training ->
                val createdTraining = plansRepository.addTraining(
                    planId = createdPlan.id,
                    weekIndex = training.weekIndex,
                    name = training.name
                )
                training.exercises.forEach { exercise ->
                    plansRepository.addExercise(
                        planId = createdPlan.id,
                        trainingId = createdTraining.id,
                        name = exercise.name,
                        sets = exercise.sets,
                        reps = exercise.reps
                    )
                }
            }
        }
    }

    companion object {

        fun get(): PlanListViewModel = SharedLibraryKoin.get()
    }
}
