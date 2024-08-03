package com.maruchin.gymster.feature.plans.exerciseform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.plans.model.PlannedExercise
import com.maruchin.gymster.data.plans.model.Reps
import com.maruchin.gymster.data.plans.model.Sets
import com.maruchin.gymster.data.plans.repository.PlansRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class ExerciseFormViewModel internal constructor(
    private val planId: String,
    private val dayId: String,
    private val exerciseId: String?,
    private val plansRepository: PlansRepository
) : ViewModel() {

    val exercise: StateFlow<PlannedExercise?> = plansRepository.observePlan(planId)
        .map { it?.findExercise(exerciseId.orEmpty()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun saveExercise(name: String, sets: Sets, reps: Reps) = viewModelScope.launch {
        if (exerciseId == null) {
            plansRepository.addExercise(
                planId = planId,
                dayId = dayId,
                name = name,
                sets = sets,
                reps = reps
            )
        } else {
            plansRepository.updateExercise(
                planId = planId,
                dayId = dayId,
                exerciseId = exerciseId,
                newName = name,
                newSets = sets,
                newReps = reps
            )
        }
    }

    companion object {

        fun get(planId: String, dayId: String, exerciseId: String?): ExerciseFormViewModel =
            SharedLibraryKoin.get { parametersOf(planId, dayId, exerciseId) }
    }
}
