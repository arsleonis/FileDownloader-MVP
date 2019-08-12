package com.zrz.android.filedownloader.model.pref

import android.content.Context
import androidx.core.content.edit
import com.zrz.android.filedownloader.R

class PrefManagerImpl(private val appContext: Context) : PrefManager {

    private val sharedPreferences = appContext.getSharedPreferences(
        appContext.getString(R.string.app_preferences),
        Context.MODE_PRIVATE
    )

    override fun isFirstLaunchPreferences(): Boolean =
        sharedPreferences.getBoolean(appContext.getString(R.string.app_first_launch), true)

    override fun changeFirstLaunchPreferences() {
        sharedPreferences.edit(commit = true) { putBoolean(appContext.getString(R.string.app_first_launch), false) }
    }
}