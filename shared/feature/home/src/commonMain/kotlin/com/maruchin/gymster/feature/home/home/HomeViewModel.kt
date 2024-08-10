package com.maruchin.gymster.feature.home.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.plans.model.samplePlans
import com.maruchin.gymster.data.plans.repository.PlansRepository
import kotlinx.coroutines.launch
import org.koin.core.component.get

class HomeViewModel(private val plansRepository: PlansRepository) : ViewModel() {

    fun seedData() = viewModelScope.launch {
        val plan = samplePlans.first()
        val planId = plansRepository.createPlan(name = plan.name)
        plan.trainings.forEach { day ->
            val dayId = plansRepository.addDay(planId = planId, name = day.name)
            day.exercises.forEach { exercise ->
                plansRepository.addExercise(
                    planId = planId,
                    dayId = dayId,
                    name = exercise.name,
                    sets = exercise.sets,
                    reps = exercise.reps
                )
            }
        }
    }

    companion object {

        fun get(): HomeViewModel = SharedLibraryKoin.get()
    }
}
