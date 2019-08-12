package com.zrz.android.filedownloader.model.pref

import com.zrz.android.filedownloader.core.base.manager.FDManager

interface PrefManager : FDManager {

    fun isFirstLaunchPreferences(): Boolean

    fun changeFirstLaunchPreferences()
}