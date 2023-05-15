package com.aks.currentlocationdetector.di.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RoomDbApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}