package com.maruchin.gymster.core.database.di

import com.maruchin.gymster.core.database.room.databaseBuilder
import org.koin.dsl.module

internal actual val platformModule = module {

    factory { databaseBuilder(get()) }
}
