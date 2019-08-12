package com.zrz.android.filedownloader.model.db.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomPDFResource(
    @PrimaryKey
    val urlAddress: String,
    @ColumnInfo(name = "file_name")
    val fileName: String,
    @ColumnInfo(name = "file_dir")
    val fileDirPath: String,
    @ColumnInfo(name = "image_name")
    val imageName: String,
    @ColumnInfo(name = "image_dir")
    val imageDirPath: String
)