package com.maruchin.gymster.planlist.newplan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.data.plans.repository.PlansRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class NewPlanViewModel internal constructor(private val plansRepository: PlansRepository) :
    ViewModel() {

    fun createPlan(name: String) = viewModelScope.launch {
        plansRepository.createPlan(name = name)
    }

    companion object : KoinComponent {

        fun create(): NewPlanViewModel = get()
    }
}
