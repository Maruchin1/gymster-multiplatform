package com.maruchin.multiplatform.gymster.shared.data.trainingplans.di

import com.maruchin.multiplatform.gymster.shared.data.trainingplans.repository.FakeTrainingPlansRepository
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.repository.TrainingPlansRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataTrainingPlansTestModule = module {

    singleOf(::FakeTrainingPlansRepository) bind TrainingPlansRepository::class
}
