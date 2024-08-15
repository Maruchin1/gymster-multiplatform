package com.maruchin.gymster

import com.maruchin.gymster.core.clock.di.coreClockModule
import com.maruchin.gymster.core.coroutines.coreCoroutinesModule
import com.maruchin.gymster.core.database.di.coreDatabaseModule
import com.maruchin.gymster.core.di.SharedLibraryKoin
import com.maruchin.gymster.data.plans.di.dataPlansModule
import com.maruchin.gymster.data.trainings.di.dataTrainingsModule
import com.maruchin.gymster.feature.home.di.featureHomeModule
import com.maruchin.gymster.feature.planeditor.di.featurePlanEditorModule
import com.maruchin.gymster.planlist.di.featurePlanListModule

private val coreModules = listOf(coreDatabaseModule, coreClockModule, coreCoroutinesModule)

private val dataModules = listOf(dataPlansModule, dataTrainingsModule)

private val featureModules =
    listOf(featureHomeModule, featurePlanListModule, featurePlanEditorModule)

fun initSharedLibrary() {
    SharedLibraryKoin.init(coreModules + dataModules + featureModules)
}
