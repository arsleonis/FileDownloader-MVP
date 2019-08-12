package com.zrz.android.filedownloader.model.file

import java.io.*

class FileManagerImpl : FileManager {
    private lateinit var file: File

    override fun writeFile(filePath: String, data: ByteArray) {
        file = File(filePath)
        val fileOutputStream: OutputStream = FileOutputStream(file)
        val byteArrayOutPutStream = ByteArrayOutputStream()
        byteArrayOutPutStream.write(data)
        byteArrayOutPutStream.writeTo(fileOutputStream)
        byteArrayOutPutStream.close()
        fileOutputStream.close()
    }

    override fun readFile(filePath: String): ByteArray {
        file = File(filePath)
        val data = ByteArray((file.length()).toInt())
        val fileInputStream: InputStream = FileInputStream(file)
        fileInputStream.read(data)
        fileInputStream.close()
        return data
    }

    override fun deleteFile(filePath: String) {
        file = File(filePath)
        file.delete()
    }

    override fun fileIsExists(filePath: String): Boolean {
        file = File(filePath)
        return file.exists()
    }

    override fun dirIsExists(dirPath: String): Boolean {
        val dir = File(dirPath)
        val isDirExists = dir.exists()
        return if (isDirExists) isDirExists else dir.mkdir()
    }
}