package com.maruchin.gymster.android

import android.app.Application
import com.maruchin.gymster.Platform
import com.maruchin.gymster.initSharedLibrary

class GymsterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val platform = Platform(context = applicationContext)
        initSharedLibrary(platform)
    }
}
