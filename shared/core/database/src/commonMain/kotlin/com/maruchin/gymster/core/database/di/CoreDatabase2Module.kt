package com.maruchin.gymster.core.database.di

import com.maruchin.gymster.core.database.room.GymsterDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

val coreDatabase2Module = module {

    includes(platformModule)

    single { GymsterDatabase.create(get()) }

    factory { get<GymsterDatabase>().planDao() }

    factory { get<GymsterDatabase>().plannedTrainingDao() }

    factory { get<GymsterDatabase>().plannedExerciseDao() }

    factory { get<GymsterDatabase>().trainingDao() }

    factory { get<GymsterDatabase>().exerciseDao() }

    factory { get<GymsterDatabase>().setResultDao() }
}

internal expect val platformModule: Module
