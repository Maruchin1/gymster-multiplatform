package com.maruchin.gymster.feature.trainings.di

import com.maruchin.gymster.feature.trainings.progresseditor.ProgressEditorViewModel
import com.maruchin.gymster.feature.trainings.trainingblocklist.TrainingBlockListViewModel
import com.maruchin.gymster.feature.trainings.trainingeditor.TrainingEditorViewModel
import org.koin.dsl.module

val featureTrainingsModule = module {

    factory { (trainingBlockId: String, weekNumber: Int, trainingId: String) ->
        TrainingEditorViewModel(trainingBlockId, weekNumber, trainingId, get())
    }

    factory { TrainingBlockListViewModel(get()) }

    factory { (
        trainingBlockId: String,
        weekNumber: Int,
        trainingId: String,
        exerciseId: String,
        progressIndex: Int
    ) ->
        ProgressEditorViewModel(
            trainingBlockId,
            weekNumber,
            trainingId,
            exerciseId,
            progressIndex,
            get()
        )
    }
}
