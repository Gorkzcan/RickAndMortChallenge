package com.gorkem.rmainviousg

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Rmapp:Application() {
    override fun onCreate() {
        super.onCreate()
    }
}