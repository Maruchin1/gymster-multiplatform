package com.maruchin.gymster.data.trainings.di

import com.maruchin.gymster.data.trainings.repository.FakeTrainingsRepository
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataTrainingsTestModule = module {

    singleOf(::FakeTrainingsRepository) bind TrainingsRepository::class
}
