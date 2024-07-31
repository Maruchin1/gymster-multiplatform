package com.maruchin.gymster.feature.plans.di

import com.maruchin.gymster.feature.plans.dayform.DayFormViewModel
import com.maruchin.gymster.feature.plans.exerciseform.ExerciseFormViewModel
import com.maruchin.gymster.feature.plans.planeditor.PlanEditorViewModel
import com.maruchin.gymster.feature.plans.planform.PlanFormViewModel
import com.maruchin.gymster.feature.plans.planlist.PlanListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val featurePlansModule = module {

    factory { (planId: String?) -> PlanFormViewModel(planId, get()) }

    factoryOf(::PlanListViewModel)

    factory { (planId: String) -> PlanEditorViewModel(planId, get()) }

    factory { (planId: String, dayId: String?) -> DayFormViewModel(planId, dayId, get()) }

    factory { (planId: String, dayId: String, exerciseId: String?) ->
        ExerciseFormViewModel(planId, dayId, exerciseId, get())
    }
}
