package com.zrz.android.filedownloader.core.base.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Parcelable
import com.zrz.android.filedownloader.BuildConfig.*
import com.zrz.android.filedownloader.R
import com.zrz.android.filedownloader.util.single.Notifications
import com.zrz.android.filedownloader.util.extention.getId
import com.zrz.android.filedownloader.util.extention.getResources
import com.zrz.android.filedownloader.util.extention.getStartCommand

abstract class BaseService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, id: Int): Int {
        when (intent?.getStartCommand(KEY_START_SERVICE_COMMAND)) {
            COMMAND_START_SERVICE_IN_BACKGROUND ->
                startServiceInBackground(
                    intent.getResources(KEY_START_SERVICE_COMMAND, KEY_PDF_RESOURCE),
                    intent.getId(KEY_START_SERVICE_COMMAND, KEY_ID)
                )
            COMMAND_START_SERVICE_IN_FOREGROUND ->
                startServiceInForeground(
                    intent.getResources(KEY_START_SERVICE_COMMAND, KEY_PDF_RESOURCE),
                    intent.getId(KEY_START_SERVICE_COMMAND, KEY_ID)
                )
            else -> stopSelf()
        }
        return START_NOT_STICKY
    }

    private fun startServiceInForeground(res: Parcelable, id: Int, channelId: String = DOWNLOAD_SERVICE_CHANNEL_ID) {
        val notification: Notification = Notifications.getNotification(
            serviceContext = this,
            channelId = channelId,
            channelName = R.string.download_service_notification_channel_name,
            channelDescription = R.string.download_service_notification_channel_description,
            notificationIcon = R.mipmap.ic_launcher_round,
            notificationTitle = R.string.download_file_from,
            notificationText = getNotificationText(res)
        )
        startForeground(channelId.toInt(), notification)
        startServiceInBackground(res, id)
    }

    abstract fun getNotificationText(res: Parcelable): String

    abstract fun startServiceInBackground(res: Parcelable, id: Int)
}