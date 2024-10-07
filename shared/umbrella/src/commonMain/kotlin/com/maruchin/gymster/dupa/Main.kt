package com.maruchin.gymster.dupa

import com.maruchin.gymster.core.clock.di.coreClockModule
import com.maruchin.gymster.core.coroutines.coreCoroutinesModule
import com.maruchin.gymster.core.database.di.coreDatabaseModule
import com.maruchin.gymster.core.database2.di.coreDatabase2Module
import com.maruchin.gymster.core.datastore.di.coreSettingsModule
import com.maruchin.gymster.data.plans.di.dataPlansModule
import com.maruchin.gymster.data.trainings.di.dataTrainingsModule
import com.maruchin.gymster.data.trainings2.di.dataTrainings2Module
import org.koin.core.context.startKoin

private val coreModules =
    listOf(
        coreDatabaseModule,
        coreDatabase2Module,
        coreClockModule,
        coreCoroutinesModule,
        coreSettingsModule
    )

private val dataModules = listOf(dataPlansModule, dataTrainingsModule, dataTrainings2Module)

fun initSharedLibrary(platform: Platform) {
    startKoin {
        modules(coreModules + dataModules + platform.module)
    }
}
