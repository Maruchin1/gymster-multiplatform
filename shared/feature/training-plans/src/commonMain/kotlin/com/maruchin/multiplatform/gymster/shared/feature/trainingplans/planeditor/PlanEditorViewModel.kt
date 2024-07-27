package com.maruchin.multiplatform.gymster.shared.feature.trainingplans.planeditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.multiplatform.gymster.shared.core.di.SharedLibraryKoin
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.model.TrainingPlan
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.repository.TrainingPlansRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class PlanEditorViewModel internal constructor(
    private val planId: String,
    private val trainingPlansRepository: TrainingPlansRepository
) : ViewModel() {

    val plan: StateFlow<TrainingPlan?> = trainingPlansRepository.observePlan(planId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun deletePlan() = viewModelScope.launch {
        trainingPlansRepository.deletePlan(planId)
    }

    companion object {

        fun get(planId: String): PlanEditorViewModel =
            SharedLibraryKoin.get { parametersOf(planId) }
    }
}
