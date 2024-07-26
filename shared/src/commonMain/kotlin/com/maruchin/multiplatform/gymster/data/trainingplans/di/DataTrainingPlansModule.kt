package com.maruchin.multiplatform.gymster.data.trainingplans.di

import com.maruchin.multiplatform.gymster.data.trainingplans.datasource.TrainingPlanLocalDataSource
import com.maruchin.multiplatform.gymster.data.trainingplans.repository.DefaultTrainingPlansRepository
import com.maruchin.multiplatform.gymster.data.trainingplans.repository.TrainingPlansRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val dataTrainingPlansModule = module {

    singleOf(::TrainingPlanLocalDataSource)

    singleOf(::DefaultTrainingPlansRepository) bind TrainingPlansRepository::class
}
