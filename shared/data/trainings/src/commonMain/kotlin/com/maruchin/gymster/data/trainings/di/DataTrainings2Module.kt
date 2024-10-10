package com.maruchin.gymster.data.trainings.di

import com.maruchin.gymster.data.trainings.datasource.TrainingsLocalDataSource
import com.maruchin.gymster.data.trainings.repository.DefaultTrainingsRepository
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val dataTrainings2Module = module {

    factory { TrainingsLocalDataSource(get(), get(), get()) }

    factory { DefaultTrainingsRepository(get()) } bind TrainingsRepository::class
}
