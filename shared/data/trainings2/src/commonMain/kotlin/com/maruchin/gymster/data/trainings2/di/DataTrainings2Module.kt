package com.maruchin.gymster.data.trainings2.di

import com.maruchin.gymster.data.trainings2.datasource.TrainingsLocalDataSource
import com.maruchin.gymster.data.trainings2.repository.DefaultTrainingsRepository
import com.maruchin.gymster.data.trainings2.repository.TrainingsRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val dataTrainings2Module = module {

    factory { TrainingsLocalDataSource(get(), get(), get()) }

    factory { DefaultTrainingsRepository(get()) } bind TrainingsRepository::class
}
