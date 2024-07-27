package com.maruchin.multiplatform.gymster.feature.trainingplans.di

import com.maruchin.multiplatform.gymster.feature.trainingplans.dayform.DayFormViewModel
import com.maruchin.multiplatform.gymster.feature.trainingplans.exerciseform.ExerciseFormViewModel
import com.maruchin.multiplatform.gymster.feature.trainingplans.planeditor.PlanEditorViewModel
import com.maruchin.multiplatform.gymster.feature.trainingplans.planform.PlanFormViewModel
import com.maruchin.multiplatform.gymster.feature.trainingplans.planlist.PlanListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val featuresTrainingPlansModule = module {

    factory { (planId: String?) -> PlanFormViewModel(planId, get()) }

    factoryOf(::PlanListViewModel)

    factory { (planId: String) -> PlanEditorViewModel(planId, get()) }

    factory { (planId: String, dayId: String?) -> DayFormViewModel(planId, dayId, get()) }

    factory { (planId: String, dayId: String, exerciseId: String?) ->
        ExerciseFormViewModel(planId, dayId, exerciseId, get())
    }
}
