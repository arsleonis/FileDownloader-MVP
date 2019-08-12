package com.zrz.android.filedownloader.model.db

import android.content.Context
import com.zrz.android.filedownloader.BuildConfig.IMAGE_POSTFIX
import com.zrz.android.filedownloader.BuildConfig.NO_IMAGE
import com.zrz.android.filedownloader.entity.PDFResource
import com.zrz.android.filedownloader.model.db.room.DBInstance
import com.zrz.android.filedownloader.model.db.room.RoomMapper

class DBManagerImpl(appContext: Context) : DBManager {

    private val dbInstance = DBInstance(appContext)
    private val database = dbInstance.getWritableDatabase()

    override fun updateEntry(pDFResource: PDFResource, isExists: Boolean): PDFResource {
        val imageName = if (isExists) getImageName(pDFResource) else NO_IMAGE
        val roomPDFResource = RoomMapper.getRoomResource(pDFResource)
        return RoomMapper.getAppResource(database.getResourceDao().updateAndRead(roomPDFResource.urlAddress, imageName))
    }

    override fun getAllDatabaseEntries(): List<PDFResource> {
        return RoomMapper.getAppResources(database.getResourceDao().getAllEntries())
    }

    private fun getImageName(pDFResource: PDFResource): String =
        "${pDFResource.fileName}$IMAGE_POSTFIX"
}