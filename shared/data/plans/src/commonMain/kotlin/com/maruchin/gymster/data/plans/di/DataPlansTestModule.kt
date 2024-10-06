package com.maruchin.gymster.data.plans.di

import com.maruchin.gymster.data.plans.repository.FakePlansRepository
import com.maruchin.gymster.data.plans.repository.FakePlansRepository2
import com.maruchin.gymster.data.plans.repository.PlansRepository
import com.maruchin.gymster.data.plans.repository.PlansRepository2
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataPlansTestModule = module {

    singleOf(::FakePlansRepository) bind PlansRepository::class

    single { FakePlansRepository2() } bind PlansRepository2::class
}
