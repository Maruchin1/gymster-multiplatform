package com.maruchin.gymster.feature.home.di

import com.maruchin.gymster.feature.home.home.HomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val featureHomeModule = module {

    factoryOf(::HomeViewModel)
}
