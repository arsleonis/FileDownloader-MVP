package com.zrz.android.filedownloader.core.config

import android.content.Context
import com.facebook.stetho.Stetho

object AppConfigurationStetho {

    fun initializeStetho(appContext: Context) {
        val initializerBuilder = Stetho.newInitializerBuilder(appContext)
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(appContext))
        initializerBuilder.enableDumpapp(Stetho.defaultDumperPluginsProvider(appContext))
        val initializer = initializerBuilder.build()
        Stetho.initialize(initializer)
    }
}