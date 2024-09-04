package com.maruchin.gymster.dupa

import android.content.Context
import org.koin.core.module.Module
import org.koin.dsl.module

actual class Platform(val context: Context) {

    internal actual val module: Module = module {

        factory { context }
    }
}
