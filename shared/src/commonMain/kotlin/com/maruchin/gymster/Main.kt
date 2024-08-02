package com.maruchin.gymster

import com.maruchin.gymster.core.clock.di.coreClockModule
import com.maruchin.gymster.core.database.di.coreDatabaseModule
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.plans.di.dataPlansModule
import com.maruchin.gymster.data.trainings.di.dataTrainingsModule
import com.maruchin.gymster.feature.plans.di.featurePlansModule
import com.maruchin.gymster.feature.trainings.di.featureTrainingsModule

private val coreModules = listOf(coreDatabaseModule, coreClockModule)

private val dataModules = listOf(dataPlansModule, dataTrainingsModule)

private val featureModules = listOf(featurePlansModule, featureTrainingsModule)

fun initSharedLibrary() {
    SharedLibraryKoin.init(coreModules + dataModules + featureModules)
}
