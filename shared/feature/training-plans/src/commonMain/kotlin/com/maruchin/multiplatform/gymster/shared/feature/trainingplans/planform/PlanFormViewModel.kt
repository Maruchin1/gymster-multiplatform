package com.maruchin.multiplatform.gymster.shared.feature.trainingplans.planform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.multiplatform.gymster.shared.core.di.SharedLibraryKoin
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.TrainingPlan
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.repository.TrainingPlansRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class PlanFormViewModel internal constructor(
    private val planId: String?,
    private val trainingPlansRepository: TrainingPlansRepository
) : ViewModel() {

    val plan: StateFlow<TrainingPlan?> = flow {
        if (planId == null) {
            emit(null)
        } else {
            emitAll(trainingPlansRepository.observePlan(planId))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    fun savePlan(name: String) = viewModelScope.launch {
        if (planId == null) {
            trainingPlansRepository.createPlan(name = name)
        } else {
            trainingPlansRepository.changePlanName(planId = planId, newName = name)
        }
    }

    companion object {

        fun get(planId: String?): PlanFormViewModel = SharedLibraryKoin.get { parametersOf(planId) }
    }
}
