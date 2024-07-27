package com.maruchin.multiplatform.gymster.shared.feature.trainingplans.di

import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.dayform.DayFormViewModel
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.exerciseform.ExerciseFormViewModel
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.planeditor.PlanEditorViewModel
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.planform.PlanFormViewModel
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.planlist.PlanListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val featureTrainingPlansModule = module {

    factory { (planId: String?) -> PlanFormViewModel(planId, get()) }

    factoryOf(::PlanListViewModel)

    factory { (planId: String) -> PlanEditorViewModel(planId, get()) }

    factory { (planId: String, dayId: String?) -> DayFormViewModel(planId, dayId, get()) }

    factory { (planId: String, dayId: String, exerciseId: String?) ->
        ExerciseFormViewModel(planId, dayId, exerciseId, get())
    }
}
