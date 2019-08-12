package com.zrz.android.filedownloader.model.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zrz.android.filedownloader.BuildConfig

@Database(entities = [RoomPDFResource::class], version = BuildConfig.DB_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getResourceDao(): PDFResourceDao
}