package com.NotifApp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

object NotificationUtils {

    const val CHANNEL_ID = "notifapp_channel"
    private const val CHANNEL_NAME = "NotifApp Channel"
    private const val CHANNEL_DESCRIPTION = "Channel untuk notifikasi NotifApp"

    fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = CHANNEL_DESCRIPTION
        }
        context.getSystemService(NotificationManager::class.java)
            .createNotificationChannel(channel)
    }
}
