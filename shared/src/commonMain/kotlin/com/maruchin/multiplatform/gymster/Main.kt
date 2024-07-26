package com.maruchin.multiplatform.gymster

import com.maruchin.multiplatform.gymster.core.database.di.coreDatabaseModule
import com.maruchin.multiplatform.gymster.core.di.SharedLibraryKoin

private val coreModules = listOf(coreDatabaseModule)

fun initSharedLibrary() {
    SharedLibraryKoin.init(coreModules)
}
