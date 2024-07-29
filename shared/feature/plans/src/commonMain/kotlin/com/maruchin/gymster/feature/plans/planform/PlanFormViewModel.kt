package com.maruchin.gymster.feature.plans.planform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.plans.model.Plan
import com.maruchin.gymster.data.plans.repository.PlansRepository
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
    private val plansRepository: PlansRepository
) : ViewModel() {

    val plan: StateFlow<Plan?> = flow {
        if (planId == null) {
            emit(null)
        } else {
            emitAll(plansRepository.observePlan(planId))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    fun savePlan(name: String) = viewModelScope.launch {
        if (planId == null) {
            plansRepository.createPlan(name = name)
        } else {
            plansRepository.changePlanName(planId = planId, newName = name)
        }
    }

    companion object {

        fun get(planId: String?): PlanFormViewModel = SharedLibraryKoin.get { parametersOf(planId) }
    }
}
