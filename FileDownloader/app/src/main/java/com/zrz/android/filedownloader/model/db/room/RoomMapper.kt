package com.zrz.android.filedownloader.model.db.room

import com.zrz.android.filedownloader.entity.PDFResource

object RoomMapper {

    fun getAppResource(roomResource: RoomPDFResource): PDFResource =
        PDFResource(
            roomResource.urlAddress,
            roomResource.fileName,
            roomResource.fileDirPath,
            roomResource.imageName,
            roomResource.imageDirPath
        )

    fun getRoomResource(appResource: PDFResource): RoomPDFResource =
        RoomPDFResource(
            appResource.urlAddress,
            appResource.fileName,
            appResource.fileDirPath,
            appResource.imageName,
            appResource.imageDirPath
        )

    fun getAppResources(roomResources: List<RoomPDFResource>): ArrayList<PDFResource> {
        val appResources = ArrayList<PDFResource>()
        for (roomResource in roomResources) {
            val appResource = PDFResource(
                roomResource.urlAddress,
                roomResource.fileName,
                roomResource.fileDirPath, roomResource.imageName,
                roomResource.imageDirPath
            )
            appResources.add(appResource)
        }
        return appResources
    }

    fun getRoomResources(appResources: ArrayList<PDFResource>): List<RoomPDFResource> {
        val roomResources = ArrayList<RoomPDFResource>()
        for (appResource in appResources) {
            val roomResource = RoomPDFResource(
                appResource.urlAddress,
                appResource.fileName,
                appResource.fileDirPath,
                appResource.imageName,
                appResource.imageDirPath
            )
            roomResources.add(roomResource)
        }
        return roomResources
    }
}