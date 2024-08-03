package com.maruchin.gymster.feature.plans.dayform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.plans.model.PlannedTraining
import com.maruchin.gymster.data.plans.repository.PlansRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class DayFormViewModel internal constructor(
    private val planId: String,
    private val dayId: String?,
    private val plansRepository: PlansRepository
) : ViewModel() {

    val day: StateFlow<PlannedTraining?> = plansRepository.observePlan(planId)
        .map { it?.findDay(dayId.orEmpty()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun saveDay(name: String) = viewModelScope.launch {
        if (dayId == null) {
            plansRepository.addDay(planId = planId, name = name)
        } else {
            plansRepository.changeDayName(planId = planId, dayId = dayId, newName = name)
        }
    }

    companion object {

        fun get(planId: String, dayId: String?): DayFormViewModel =
            SharedLibraryKoin.get { parametersOf(planId, dayId) }
    }
}
