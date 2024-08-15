package com.maruchin.gymster.feature.planeditor.di

import com.maruchin.gymster.feature.planeditor.durationform.DurationFormViewModel
import com.maruchin.gymster.feature.planeditor.exerciseform.ExerciseFormViewModel
import com.maruchin.gymster.feature.planeditor.planeditor.PlanEditorViewModel
import com.maruchin.gymster.feature.planeditor.planform.PlanFormViewModel
import com.maruchin.gymster.feature.planeditor.trainingform.TrainingFormViewModel
import org.koin.dsl.module

val featurePlanEditorModule = module {

    factory { (planId: String) -> DurationFormViewModel(planId, get()) }

    factory { (planId: String, trainingId: String, exerciseId: String?) ->
        ExerciseFormViewModel(planId, trainingId, exerciseId, get())
    }

    factory { (planId: String) -> PlanEditorViewModel(planId, get()) }

    factory { (planId: String) -> PlanFormViewModel(planId, get()) }

    factory { (planId: String, trainingId: String) ->
        TrainingFormViewModel(planId, trainingId, get())
    }
}
