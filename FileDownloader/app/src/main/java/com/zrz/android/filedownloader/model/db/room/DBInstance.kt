package com.zrz.android.filedownloader.model.db.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zrz.android.filedownloader.BuildConfig.DB_NAME
import com.zrz.android.filedownloader.core.di.AppInjectionManager
import com.zrz.android.filedownloader.model.db.data.FirstDataCreator
import com.zrz.android.filedownloader.model.pref.PrefManager
import java.util.concurrent.Executors

class DBInstance(appContext: Context) {
    private val prefManager = AppInjectionManager.getRequiredManager<PrefManager>()
    private val isAppFirstLaunch = prefManager.isFirstLaunchPreferences()
    private val roomCallback = object : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            if (isAppFirstLaunch) firstDatabaseFilling()
        }
    }
    private val database = Room
        .databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DB_NAME
        )
        .addCallback(roomCallback)
        .build()

    init {
        if (isAppFirstLaunch) prefManager.changeFirstLaunchPreferences()
    }

    private fun firstDatabaseFilling() {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            database.getResourceDao().firstFilling(RoomMapper.getRoomResources(FirstDataCreator.getFirstData()))
        }
    }

    fun getWritableDatabase(): AppDatabase {
        database.openHelper.writableDatabase
        return database
    }
}