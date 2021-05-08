package com.kilanny.autobrightness

import android.app.ActivityManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import java.util.*

private var isRefreshing = false

fun refreshBrightness(context: Context, timeoutSeconds: Int = 7) {
    if (isRefreshing)
        return
    isRefreshing = true
    try {
        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
        )
        Toast.makeText(
            context, String.format(
                Locale.ENGLISH,
                "Turning-on Auto Brightness for %d seconds", timeoutSeconds
            ), Toast.LENGTH_LONG
        ).show()
    } catch (ex: Throwable) {
        ex.printStackTrace()
        isRefreshing = false
        return
    }
    val timer = Timer()
    val handler = Handler(Looper.getMainLooper())
    val timerTask = object : TimerTask() {
        override fun run() {
            handler.post(kotlinx.coroutines.Runnable {
                Settings.System.putInt(
                    context.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
                )
                isRefreshing = false
                Toast.makeText(context, "Turned-off Auto Brightness", Toast.LENGTH_LONG).show()
            })
        }
    }
    timer.schedule(timerTask, (timeoutSeconds * 1000).toLong())
}

fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
    val manager = context
        .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Int.MAX_VALUE)) {
        if (serviceClass.name == service.service.className) {
            return true
        }
    }
    return false
}