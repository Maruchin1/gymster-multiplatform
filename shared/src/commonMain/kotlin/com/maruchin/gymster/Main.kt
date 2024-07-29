package com.maruchin.gymster

import com.maruchin.gymster.core.database.di.coreDatabaseModule
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.plans.di.dataPlansModule
import com.maruchin.gymster.feature.plans.di.featureTrainingPlansModule

private val coreModules = listOf(coreDatabaseModule)

private val dataModules = listOf(dataPlansModule)

private val featureModules = listOf(featureTrainingPlansModule)

fun initSharedLibrary() {
    SharedLibraryKoin.init(coreModules + dataModules + featureModules)
}
