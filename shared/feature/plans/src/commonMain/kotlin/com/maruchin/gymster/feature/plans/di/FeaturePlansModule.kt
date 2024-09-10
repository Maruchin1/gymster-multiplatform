package com.maruchin.gymster.feature.plans.di

import com.maruchin.gymster.feature.plans.planeditor.PlanEditorViewModel
import com.maruchin.gymster.feature.plans.planlist.PlanListViewModel
import org.koin.dsl.module

val featurePlansModule = module {

    factory { PlanListViewModel(get()) }

    factory { PlanEditorViewModel(it[0], get()) }
}
