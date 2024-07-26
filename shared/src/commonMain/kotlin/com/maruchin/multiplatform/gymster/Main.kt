package com.maruchin.multiplatform.gymster

import com.maruchin.multiplatform.gymster.core.database.di.coreDatabaseModule
import com.maruchin.multiplatform.gymster.core.di.SharedLibraryKoin
import com.maruchin.multiplatform.gymster.data.trainingplans.di.dataTrainingPlansModule

private val coreModules = listOf(coreDatabaseModule)

private val dataModules = listOf(dataTrainingPlansModule)

fun initSharedLibrary() {
    SharedLibraryKoin.init(coreModules + dataModules)
}
