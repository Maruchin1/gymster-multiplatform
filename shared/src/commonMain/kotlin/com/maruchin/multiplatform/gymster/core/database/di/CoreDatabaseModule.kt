package com.maruchin.multiplatform.gymster.core.database.di

import com.maruchin.multiplatform.gymster.core.database.createRealmDatabase
import org.koin.dsl.module

internal val coreDatabaseModule = module {

    single { createRealmDatabase() }
}
