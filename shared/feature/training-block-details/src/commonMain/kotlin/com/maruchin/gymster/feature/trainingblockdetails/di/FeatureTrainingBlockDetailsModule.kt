package com.maruchin.gymster.feature.trainingblockdetails.di

import com.maruchin.gymster.feature.trainingblockdetails.progressform.ProgressFormViewModel
import com.maruchin.gymster.feature.trainingblockdetails.timeline.TimelineViewModel
import org.koin.dsl.module

val featureTrainingBlockDetailsModule = module {

    factory { (trainingBlockId: String) -> TimelineViewModel(trainingBlockId, get()) }

    factory { (
        trainingBlockId: String,
        weekNumber: Int,
        trainingId: String,
        exerciseId: String,
        progressIndex: Int
    ) ->
        ProgressFormViewModel(
            trainingBlockId,
            weekNumber,
            trainingId,
            exerciseId,
            progressIndex,
            get()
        )
    }
}
