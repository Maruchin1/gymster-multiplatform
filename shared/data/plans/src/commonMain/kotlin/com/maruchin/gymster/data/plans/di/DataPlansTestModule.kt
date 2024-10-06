package com.maruchin.gymster.data.plans.di

import com.maruchin.gymster.data.plans.repository.FakePlansRepository
import com.maruchin.gymster.data.plans.repository.PlansRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val dataPlansTestModule = module {

    single { FakePlansRepository() } bind PlansRepository::class
}
