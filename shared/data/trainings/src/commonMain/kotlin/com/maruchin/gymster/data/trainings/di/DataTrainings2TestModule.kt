package com.maruchin.gymster.data.trainings.di

import com.maruchin.gymster.data.trainings.repository.FakeTrainingsRepository
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val dataTrainings2TestModule = module {

    single { FakeTrainingsRepository() } bind TrainingsRepository::class
}
