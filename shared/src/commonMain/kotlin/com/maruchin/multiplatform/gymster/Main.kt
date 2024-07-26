package com.maruchin.multiplatform.gymster

import com.maruchin.multiplatform.gymster.core.di.SharedLibraryKoin
import org.koin.core.module.Module

private val modules = listOf<Module>()

fun initSharedLibrary() {
    SharedLibraryKoin.init(modules)
}
