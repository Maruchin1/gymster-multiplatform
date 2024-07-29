package com.maruchin.gymster.data.trainings.di

import com.maruchin.gymster.data.trainings.datasource.TrainingsLocalDataSource
import com.maruchin.gymster.data.trainings.repository.DefaultTrainingsRepository
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataTrainingsModule = module {

    singleOf(::TrainingsLocalDataSource)

    singleOf(::DefaultTrainingsRepository) bind TrainingsRepository::class
}
