package com.maruchin.gymster.data.trainings.di

import com.maruchin.gymster.data.trainings.datasource.ActiveTrainingLocalDataSource
import com.maruchin.gymster.data.trainings.datasource.TrainingsLocalDataSource
import com.maruchin.gymster.data.trainings.repository.DefaultTrainingsRepository
import com.maruchin.gymster.data.trainings.repository.TrainingsRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val dataTrainingsModule = module {

    factory { ActiveTrainingLocalDataSource(get()) }

    factory { TrainingsLocalDataSource(get()) }

    factory { DefaultTrainingsRepository(get(), get(), get()) } bind TrainingsRepository::class
}
