package com.maruchin.gymster.feature.planeditor.trainingform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.plans.model.PlannedTraining
import com.maruchin.gymster.data.plans.repository.PlansRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class TrainingFormViewModel internal constructor(
    private val planId: String,
    private val weekIndex: Int,
    private val trainingId: String?,
    private val plansRepository: PlansRepository
) : ViewModel() {

    val training: StateFlow<PlannedTraining?> = plansRepository.observePlan(planId)
        .filterNotNull()
        .map { it.getTraining(trainingId.orEmpty()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun saveTraining(name: String) = viewModelScope.launch {
        if (trainingId == null) {
            plansRepository.addTraining(planId = planId, weekIndex = weekIndex, name = name)
        } else {
            plansRepository.changeTrainingName(
                planId = planId,
                trainingId = trainingId,
                newName = name
            )
        }
    }

    companion object {

        fun get(planId: String, trainingId: String?): TrainingFormViewModel =
            SharedLibraryKoin.get { parametersOf(planId, trainingId) }
    }
}
