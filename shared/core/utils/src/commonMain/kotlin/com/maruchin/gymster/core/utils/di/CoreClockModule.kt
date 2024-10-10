package com.maruchin.gymster.core.utils.di

import kotlinx.datetime.Clock
import org.koin.dsl.bind
import org.koin.dsl.module

val coreClockModule = module {

    factory { Clock.System } bind Clock::class
}
