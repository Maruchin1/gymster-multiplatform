package com.maruchin.gymster.core.clock.di

import com.maruchin.gymster.core.clock.FakeClock
import kotlinx.datetime.Clock
import org.koin.dsl.bind
import org.koin.dsl.module

val coreClockTestModule = module {

    single { FakeClock() } bind Clock::class
}
