package com.kilanny.autobrightness

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class BootCompletedBroadcastReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        // no need to start service here; already in App.onCreate
//        if (context != null) {
//            refreshBrightness(context)
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
//                && !isServiceRunning(context, ScreenUnlockRefreshBrightnessService::class.java)
//            ) {
//                context.startForegroundService(
//                    Intent(
//                        context,
//                        ScreenUnlockRefreshBrightnessService::class.java
//                    )
//                )
//            }
//        }
    }
}