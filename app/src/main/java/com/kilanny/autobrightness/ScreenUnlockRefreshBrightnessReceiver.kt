package com.kilanny.autobrightness

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ScreenUnlockRefreshBrightnessReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_USER_PRESENT -> {
                if (context != null) {
                    refreshBrightness(context)
                }
            }
        }
    }
}