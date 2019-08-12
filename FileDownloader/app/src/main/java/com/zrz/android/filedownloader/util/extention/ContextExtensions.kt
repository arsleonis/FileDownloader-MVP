package com.zrz.android.filedownloader.util.extention

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.zrz.android.filedownloader.BuildConfig.KEY_FILE_PATH
import java.io.Serializable

inline fun <reified A> AppCompatActivity.startNewActivity(vararg extras: Pair<String, Serializable>)
        where A : AppCompatActivity {
    val intent = Intent(this, A::class.java)
    val list = listOf(*extras)
    if (list.isNotEmpty()) {
        val bundle = Bundle()
        list.forEach { (key, extra) -> bundle.putSerializable(key.asBundleKey(), extra) }
        intent.putExtra(KEY_FILE_PATH.asIntentKey(), bundle)
    }
    startActivity(intent)
}

inline fun <reified S> Context.startNewService(
    command: Pair<String, String>,
    id: Pair<String, Int>,
    vararg resources: Pair<String, Parcelable>
)
        where S : Service {
    val intent = Intent(this, S::class.java)
    val bundle = Bundle()
    bundle.putString(command.first.asBundleKey(), command.second)
    bundle.putInt(id.first.asBundleKey(), id.second)
    val list = listOf(*resources)
    list.forEach { (key, resource) ->
        bundle.putParcelable(key.asBundleKey(), resource)
    }
    intent.putExtra(command.first.asIntentKey(), bundle)
    startService(intent)
}

fun Context.createServiceNotificationChannel(
    channelId: String,
    @StringRes channelName: Int,
    @StringRes channelDescription: Int
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = this.getString(channelName)
        val description = this.getString(channelDescription)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val notificationChannel = NotificationChannel(channelId, name, importance)
        notificationChannel.description = description
        val notificationManager =
            this.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}