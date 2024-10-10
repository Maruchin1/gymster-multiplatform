package com.maruchin.gymster.core.utils.di

import com.maruchin.gymster.core.utils.FakeClock
import kotlinx.datetime.Clock
import org.koin.dsl.bind
import org.koin.dsl.module

val coreClockTestModule = module {

    factory { FakeClock() } bind Clock::class
}
