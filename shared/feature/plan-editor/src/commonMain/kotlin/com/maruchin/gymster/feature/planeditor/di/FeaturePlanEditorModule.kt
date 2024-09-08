package com.maruchin.gymster.feature.planeditor.di

import com.maruchin.gymster.feature.planeditor.planeditor.PlanEditorViewModel
import org.koin.dsl.module

val featurePlanEditorModule = module {

    factory { PlanEditorViewModel(it[0], get()) }
}
