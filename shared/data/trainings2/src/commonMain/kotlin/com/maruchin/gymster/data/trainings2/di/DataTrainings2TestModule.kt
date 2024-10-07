package com.maruchin.gymster.data.trainings2.di

import com.maruchin.gymster.data.trainings2.repository.FakeTrainingsRepository
import com.maruchin.gymster.data.trainings2.repository.TrainingsRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val dataTrainings2TestModule = module {

    single { FakeTrainingsRepository() } bind TrainingsRepository::class
}
