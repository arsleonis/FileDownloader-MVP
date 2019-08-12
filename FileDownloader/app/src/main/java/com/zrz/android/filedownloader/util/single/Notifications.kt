package com.zrz.android.filedownloader.util.single

import android.app.Notification
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import com.zrz.android.filedownloader.util.extention.createServiceNotificationChannel

object Notifications {

    fun getNotification(
        serviceContext: Context,
        channelId: String,
        @StringRes channelName: Int,
        @StringRes channelDescription: Int,
        @DrawableRes notificationIcon: Int,
        @StringRes notificationTitle: Int,
        notificationText: String
    ): Notification {
        serviceContext.createServiceNotificationChannel(
            channelId,
            channelName,
            channelDescription
        )
        return NotificationCompat.Builder(serviceContext, channelId).apply {
            setSmallIcon(notificationIcon)
            setContentTitle(serviceContext.getString(notificationTitle))
            setContentText(notificationText)
        }.build()
    }
}