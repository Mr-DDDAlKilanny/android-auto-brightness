package com.kilanny.autobrightness

import android.app.Application
import android.content.Intent
import android.os.Build

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        refreshBrightness(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(
                Intent(
                    this,
                    ScreenUnlockRefreshBrightnessService::class.java
                )
            )
        }
    }
}