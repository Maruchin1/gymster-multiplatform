package com.maruchin.multiplatform.gymster.android

import android.app.Application
import com.maruchin.multiplatform.gymster.initSharedLibrary

class GymsterApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initSharedLibrary()
    }
}
