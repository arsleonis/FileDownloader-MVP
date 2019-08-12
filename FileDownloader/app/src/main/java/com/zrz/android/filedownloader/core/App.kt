package com.zrz.android.filedownloader.core

import android.app.Application
import com.zrz.android.filedownloader.core.config.AppConfiguration
import com.zrz.android.filedownloader.core.di.AppInjection

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppInjection.initializeAll(this)
        AppConfiguration.initializeAll(this)
    }
}