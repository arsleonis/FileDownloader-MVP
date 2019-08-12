package com.zrz.android.filedownloader.core.di

import android.content.Context
import com.zrz.android.filedownloader.core.base.manager.FDManager
import com.zrz.android.filedownloader.core.lifecycle.AppLifecycle
import com.zrz.android.filedownloader.model.db.DBManager
import com.zrz.android.filedownloader.model.db.DBManagerImpl
import com.zrz.android.filedownloader.model.file.FileManager
import com.zrz.android.filedownloader.model.file.FileManagerImpl
import com.zrz.android.filedownloader.model.network.NetworkManager
import com.zrz.android.filedownloader.model.network.NetworkManagerImpl
import com.zrz.android.filedownloader.model.pref.PrefManager
import com.zrz.android.filedownloader.model.pref.PrefManagerImpl
import com.zrz.android.filedownloader.model.presenter.PresenterManager
import com.zrz.android.filedownloader.model.presenter.PresenterManagerImpl

object AppInjectionManager {
    val managers: ArrayList<FDManager> = ArrayList()

    fun initializeManagers(context: Context) {
        managers.add(PrefManagerImpl(context) as PrefManager)
        managers.add(PresenterManagerImpl() as PresenterManager)
        managers.add(DBManagerImpl(context) as DBManager)
        managers.add(NetworkManagerImpl(context, AppLifecycle.appLifecycle) as NetworkManager)
        managers.add(FileManagerImpl() as FileManager)
    }

    inline fun <reified M : FDManager> getRequiredManager(): M =
        managers.single { it is M } as M
}