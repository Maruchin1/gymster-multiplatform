package com.maruchin.gymster.core.database2.di

import com.maruchin.gymster.core.database2.room.databaseBuilder
import org.koin.dsl.module

internal actual val platformModule = module {

    factory { databaseBuilder() }
}
