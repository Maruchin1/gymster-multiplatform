package com.maruchin.gymster.feature.trainingeditor.di

import com.maruchin.gymster.feature.trainingeditor.editor.TrainingEditorViewModel
import org.koin.dsl.module

val featureTrainingEditorModule = module {

    factory { TrainingEditorViewModel(it[0], it[1], get()) }
}
