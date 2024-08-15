package com.maruchin.gymster.planlist.di

import com.maruchin.gymster.planlist.newplan.NewPlanViewModel
import com.maruchin.gymster.planlist.planlist.PlanListViewModel
import com.maruchin.gymster.planlist.trainingblockform.TrainingBlockFormViewModel
import org.koin.dsl.module

val featurePlanListModule = module {

    factory { PlanListViewModel(get()) }

    factory { (planId: String) -> TrainingBlockFormViewModel(planId, get(), get()) }

    factory { NewPlanViewModel(get()) }
}
