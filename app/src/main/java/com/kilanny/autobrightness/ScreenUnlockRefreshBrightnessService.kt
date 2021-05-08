package com.kilanny.autobrightness

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

@RequiresApi(Build.VERSION_CODES.O)
class ScreenUnlockRefreshBrightnessService : Service() {

    private val NOTIFICATION_ID = 1442
    private val CHANNEL_ID = "com.kilanny.autobrightness.ScreenUnlockRefreshBrightnessService"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channelName = "Screen Unlock Refresh Brightness Service"
        val channel = NotificationChannel(
            CHANNEL_ID, channelName,
            NotificationManager.IMPORTANCE_MIN
        )
        channel.lightColor = Color.BLUE
        channel.enableLights(false)
        channel.description = channelName
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nm.createNotificationChannel(channel)
        val remoteViews = RemoteViews(packageName, R.layout.notification_statusbar)

        startForeground(
            NOTIFICATION_ID,
            NotificationCompat.Builder(this, channelName)
                .setContent(remoteViews)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                .setOngoing(true)
                .setAutoCancel(false)
                .setChannelId(CHANNEL_ID)
                .setLocalOnly(true)
                .setUsesChronometer(false)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()
        )

        val broadCastReceiver = object : BroadcastReceiver() {
            override fun onReceive(contxt: Context?, intent: Intent?) {
                when (intent?.action) {
                    Intent.ACTION_USER_PRESENT -> {
                        refreshBrightness(this@ScreenUnlockRefreshBrightnessService)
                    }
                }
            }
        }
        val filter = IntentFilter()
        //filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_USER_PRESENT)
        registerReceiver(broadCastReceiver, filter)

        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}