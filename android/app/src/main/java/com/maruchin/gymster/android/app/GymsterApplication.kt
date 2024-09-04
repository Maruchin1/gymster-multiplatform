package com.maruchin.gymster.android.app

import android.app.Application
import com.maruchin.gymster.dupa.Platform
import com.maruchin.gymster.dupa.initSharedLibrary

class GymsterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val platform = Platform(context = applicationContext)
        initSharedLibrary(platform)
    }
}
