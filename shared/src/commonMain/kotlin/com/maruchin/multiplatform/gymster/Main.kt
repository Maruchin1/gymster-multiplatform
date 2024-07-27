package com.maruchin.multiplatform.gymster

import com.maruchin.multiplatform.gymster.shared.core.database.di.coreDatabaseModule
import com.maruchin.multiplatform.gymster.shared.core.di.SharedLibraryKoin
import com.maruchin.multiplatform.gymster.shared.data.trainingplans.di.dataTrainingPlansModule
import com.maruchin.multiplatform.gymster.shared.feature.trainingplans.di.featureTrainingPlansModule

private val coreModules = listOf(coreDatabaseModule)

private val dataModules = listOf(dataTrainingPlansModule)

private val featureModules = listOf(featureTrainingPlansModule)

fun initSharedLibrary() {
    SharedLibraryKoin.init(coreModules + dataModules + featureModules)
}
