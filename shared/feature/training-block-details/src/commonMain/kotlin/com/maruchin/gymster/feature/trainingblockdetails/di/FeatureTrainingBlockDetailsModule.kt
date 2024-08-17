package com.maruchin.gymster.feature.trainingblockdetails.di

import com.maruchin.gymster.feature.trainingblockdetails.progressform.ProgressFormViewModel
import com.maruchin.gymster.feature.trainingblockdetails.timeline.TimelineViewModel
import org.koin.dsl.module

val featureTrainingBlockDetailsModule = module {

    factory { TimelineViewModel(it[0], get()) }

    factory { ProgressFormViewModel(it[0], it[1], get()) }
}
