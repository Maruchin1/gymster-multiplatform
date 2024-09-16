package com.maruchin.gymster.feature.trainings.di

import com.maruchin.gymster.feature.trainings.starttrainingblock.StartTrainingBlockViewModel
import com.maruchin.gymster.feature.trainings.trainingblockeditor.TrainingBlockEditorViewModel
import com.maruchin.gymster.feature.trainings.trainingblocklist.TrainingBlockListViewModel
import com.maruchin.gymster.feature.trainings.trainingeditor.TrainingEditorViewModel
import org.koin.dsl.module

val featureTrainingsModule = module {

    factory { TrainingBlockListViewModel(get(), get()) }

    factory { StartTrainingBlockViewModel(get(), get()) }

    factory { TrainingBlockEditorViewModel(get(), get()) }

    factory { TrainingEditorViewModel(it[0], it[1], it[2], get()) }
}
