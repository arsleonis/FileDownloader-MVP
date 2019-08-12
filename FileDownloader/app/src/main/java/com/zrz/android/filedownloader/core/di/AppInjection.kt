package com.zrz.android.filedownloader.core.di

import android.content.Context

object AppInjection {

    fun initializeAll(appContext: Context) {
        AppInjectionAsync.initializeAsyncComponents()
        AppInjectionManager.initializeManagers(appContext)
    }
}