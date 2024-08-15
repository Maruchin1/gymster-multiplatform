package com.maruchin.gymster.planlist.newplan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.plans.repository.PlansRepository
import kotlinx.coroutines.launch
import org.koin.core.component.get

class NewPlanViewModel internal constructor(private val plansRepository: PlansRepository) :
    ViewModel() {

    fun createPlan(name: String) = viewModelScope.launch {
        plansRepository.createPlan(name = name)
    }

    companion object {

        fun get(): NewPlanViewModel = SharedLibraryKoin.get()
    }
}
