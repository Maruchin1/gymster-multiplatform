package com.maruchin.gymster.data.plans.di

import com.maruchin.gymster.data.plans.datasource.PlansLocalDataSource
import com.maruchin.gymster.data.plans.repository.DefaultPlansRepository
import com.maruchin.gymster.data.plans.repository.PlansRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val dataPlansModule = module {

    factory { PlansLocalDataSource(get(), get(), get()) }

    factory { DefaultPlansRepository(get()) } bind PlansRepository::class
}
