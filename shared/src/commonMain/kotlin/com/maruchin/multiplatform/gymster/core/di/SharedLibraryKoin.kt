package com.maruchin.multiplatform.gymster.core.di

import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.module.Module
import org.koin.dsl.koinApplication

internal object SharedLibraryKoin : KoinComponent {

    private var koinApplication: KoinApplication? = null

    override fun getKoin(): Koin =
        checkNotNull(koinApplication?.koin) { "SharedLibraryKoin not initialized" }

    fun init(modules: List<Module>) {
        koinApplication = koinApplication {
            modules(modules)
        }
    }
}
