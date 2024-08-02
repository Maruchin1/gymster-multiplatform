package com.maruchin.gymster.feature.trainings.di

import com.maruchin.gymster.feature.trainings.planpicker.PlanPickerViewModel
import com.maruchin.gymster.feature.trainings.traininghistory.TrainingHistoryViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val featureTrainingsModule = module {

    factoryOf(::TrainingHistoryViewModel)

    factoryOf(::PlanPickerViewModel)
}
