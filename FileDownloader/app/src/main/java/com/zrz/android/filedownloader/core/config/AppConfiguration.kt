package com.zrz.android.filedownloader.core.config

import android.content.Context

object AppConfiguration {

    fun initializeAll(appContext: Context) {
        AppConfigurationStetho.initializeStetho(appContext)
    }
}