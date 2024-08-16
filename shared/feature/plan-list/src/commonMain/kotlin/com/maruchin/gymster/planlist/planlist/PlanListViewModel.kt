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
            val planId = plansRepository.createPlan(plan.name)
            plansRepository.changePlanDuration(planId, plan.weeksDuration)
            plan.trainings.forEach { training ->
                val trainingId = plansRepository.addTraining(planId, training.name)
                training.exercises.forEach { exercise ->
                    plansRepository.addExercise(
                        planId,
                        trainingId,
                        exercise.name,
                        exercise.sets,
                        exercise.reps
                    )
                }
            }
        }
    }

    companion object {

        fun get(): PlanListViewModel = SharedLibraryKoin.get()
    }
}
