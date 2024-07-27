package com.maruchin.multiplatform.gymster

import com.maruchin.multiplatform.gymster.core.database.di.coreDatabaseModule
import com.maruchin.multiplatform.gymster.core.di.SharedLibraryKoin
import com.maruchin.multiplatform.gymster.data.trainingplans.di.dataTrainingPlansModule
import com.maruchin.multiplatform.gymster.feature.trainingplans.di.featuresTrainingPlansModule

private val coreModules = listOf(coreDatabaseModule)

private val dataModules = listOf(dataTrainingPlansModule)

private val featureModules = listOf(featuresTrainingPlansModule)

fun initSharedLibrary() {
    SharedLibraryKoin.init(coreModules + dataModules + featureModules)
}
