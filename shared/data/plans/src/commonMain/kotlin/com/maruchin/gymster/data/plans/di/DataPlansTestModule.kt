package com.maruchin.gymster.data.plans.di

import com.maruchin.gymster.data.plans.repository.FakePlansRepository
import com.maruchin.gymster.data.plans.repository.PlansRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataPlansTestModule = module {

    singleOf(::FakePlansRepository) bind PlansRepository::class
}
