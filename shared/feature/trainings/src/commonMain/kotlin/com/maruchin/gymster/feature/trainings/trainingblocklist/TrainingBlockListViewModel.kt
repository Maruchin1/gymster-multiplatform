package com.maruchin.gymster.feature.trainings.trainingblocklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.data.plans.repository.PlansRepository
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TrainingBlockListViewModel :
    ViewModel(),
    KoinComponent {

    private val trainingsRepository: TrainingsRepository by inject()
    private val plansRepository: PlansRepository by inject()

    val uiState: StateFlow<TrainingBlockListUiState> =
        trainingsRepository.observeAllTrainingBlocks()
            .map { TrainingBlockListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = TrainingBlockListUiState()
            )

    fun createTrainingBlock(planId: String, startDate: LocalDate, weekDuration: Int) =
        viewModelScope.launch {
            val plan = plansRepository.observePlan(planId).first()
            checkNotNull(plan)
            trainingsRepository.createTrainingBlock(plan, startDate, weekDuration)
        }
}
