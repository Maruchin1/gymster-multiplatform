package com.maruchin.gymster.planlist.trainingblockform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.plans.repository.PlansRepository
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class TrainingBlockFormViewModel internal constructor(
    private val planId: String,
    private val plansRepository: PlansRepository,
    private val trainingsRepository: TrainingsRepository
) : ViewModel() {

    fun createTrainingBlock(startDate: LocalDate) = viewModelScope.launch {
        val plan = plansRepository.observePlan(planId).first() ?: return@launch
        trainingsRepository.createTrainingBlock(plan, startDate, 8)
    }

    companion object {

        fun get(planId: String): TrainingBlockFormViewModel =
            SharedLibraryKoin.get { parametersOf(planId) }
    }
}
