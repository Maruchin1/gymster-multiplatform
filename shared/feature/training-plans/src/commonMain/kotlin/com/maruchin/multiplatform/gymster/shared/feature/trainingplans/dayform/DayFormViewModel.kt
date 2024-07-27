package com.maruchin.multiplatform.gymster.shared.feature.trainingplans.dayform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.multiplatform.gymster.shared.core.di.SharedLibraryKoin
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.TrainingPlanDay
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.findDay
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.repository.TrainingPlansRepository
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
    private val trainingPlansRepository: TrainingPlansRepository
) : ViewModel() {

    val day: StateFlow<TrainingPlanDay?> = trainingPlansRepository.observePlan(planId)
        .map { it?.findDay(dayId.orEmpty()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun saveDay(name: String) = viewModelScope.launch {
        if (dayId == null) {
            trainingPlansRepository.addDay(planId = planId, name = name)
        } else {
            trainingPlansRepository.changeDayName(planId = planId, dayId = dayId, newName = name)
        }
    }

    companion object {

        fun get(planId: String, dayId: String?): DayFormViewModel =
            SharedLibraryKoin.get { parametersOf(planId, dayId) }
    }
}
