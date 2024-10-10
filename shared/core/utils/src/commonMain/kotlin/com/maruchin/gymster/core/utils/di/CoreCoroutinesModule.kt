package com.maruchin.gymster.core.utils.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val coreCoroutinesModule = module {

    single { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
}
