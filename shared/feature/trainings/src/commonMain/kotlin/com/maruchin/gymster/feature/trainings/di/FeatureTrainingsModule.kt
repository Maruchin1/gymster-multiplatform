package com.maruchin.gymster.feature.trainings.di

import com.maruchin.gymster.feature.trainings.trainingblocklist.TrainingBlockListViewModel
import org.koin.dsl.module

val featureTrainingsModule = module {

    factory { TrainingBlockListViewModel(get(), get()) }
}
