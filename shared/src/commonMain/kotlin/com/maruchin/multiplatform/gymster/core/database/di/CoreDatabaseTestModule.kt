package com.maruchin.multiplatform.gymster.core.database.di

import com.maruchin.multiplatform.gymster.core.database.createTestRealmDatabase
import org.koin.dsl.module

internal val coreDatabaseTestModule = module {

    single { createTestRealmDatabase() }
}
