package com.maruchin.gymster.feature.planeditor.di

import com.maruchin.gymster.feature.planeditor.durationform.DurationFormViewModel
import com.maruchin.gymster.feature.planeditor.exerciseform.ExerciseFormViewModel
import com.maruchin.gymster.feature.planeditor.planeditor.PlanEditorViewModel
import com.maruchin.gymster.feature.planeditor.planform.PlanFormViewModel
import com.maruchin.gymster.feature.planeditor.trainingform.TrainingFormViewModel
import org.koin.dsl.module

val featurePlanEditorModule = module {

    factory { DurationFormViewModel(it[0], get()) }

    factory { ExerciseFormViewModel(it[0], it[1], it[2], get()) }

    factory { PlanEditorViewModel(it[0], get()) }

    factory { PlanFormViewModel(it[0], get()) }

    factory { TrainingFormViewModel(it[0], it[1], it[2], get()) }
}
