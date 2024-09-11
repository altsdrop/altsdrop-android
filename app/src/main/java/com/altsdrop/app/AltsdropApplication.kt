package com.altsdrop.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AltsdropApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}