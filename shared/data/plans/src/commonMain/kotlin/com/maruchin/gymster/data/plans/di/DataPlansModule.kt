package com.maruchin.gymster.data.plans.di

import com.maruchin.gymster.data.plans.datasource.PlansLocalDataSource2
import com.maruchin.gymster.data.plans.repository.DefaultPlansRepository2
import com.maruchin.gymster.data.plans.repository.PlansRepository2
import org.koin.dsl.bind
import org.koin.dsl.module

val dataPlansModule = module {

    factory { PlansLocalDataSource2(get(), get(), get()) }

    factory { DefaultPlansRepository2(get()) } bind PlansRepository2::class
}
