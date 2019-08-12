package com.zrz.android.filedownloader.model.file

import com.zrz.android.filedownloader.core.base.manager.FDManager

interface FileManager : FDManager {

    fun writeFile(filePath: String, data: ByteArray)

    fun readFile(filePath: String): ByteArray

    fun deleteFile(filePath: String)

    fun dirIsExists(dirPath: String): Boolean

    fun fileIsExists(filePath: String): Boolean
}