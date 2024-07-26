package com.maruchin.multiplatform.gymster.data.trainingplans.di

import com.maruchin.multiplatform.gymster.data.trainingplans.repository.FakeTrainingPlansRepository
import com.maruchin.multiplatform.gymster.data.trainingplans.repository.TrainingPlansRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val dataTrainingPlansTestModule = module {

    singleOf(::FakeTrainingPlansRepository) bind TrainingPlansRepository::class
}
