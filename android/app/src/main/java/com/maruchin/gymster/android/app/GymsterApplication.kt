package com.maruchin.gymster.android.app

import android.app.Application
import com.maruchin.gymster.umbrella.Platform
import com.maruchin.gymster.umbrella.initSharedLibrary

class GymsterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val platform = Platform(context = applicationContext)
        initSharedLibrary(platform)
    }
}
