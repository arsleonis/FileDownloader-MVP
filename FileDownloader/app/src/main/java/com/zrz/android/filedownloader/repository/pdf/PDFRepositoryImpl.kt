package com.zrz.android.filedownloader.repository.pdf

import android.content.Context
import android.graphics.Bitmap
import com.shockwave.pdfium.PdfiumCore
import com.zrz.android.filedownloader.BuildConfig
import com.zrz.android.filedownloader.BuildConfig.IMAGE_POSTFIX
import com.zrz.android.filedownloader.R
import com.zrz.android.filedownloader.core.observer.Observable
import com.zrz.android.filedownloader.core.observer.Observer
import com.zrz.android.filedownloader.entity.PDFResource
import com.zrz.android.filedownloader.model.db.DBManager
import com.zrz.android.filedownloader.model.file.FileManager
import com.zrz.android.filedownloader.model.network.NetworkManager
import java.io.ByteArrayOutputStream

class PDFRepositoryImpl(
    private val appContext: Context,
    private val dbManager: DBManager,
    private val fileManager: FileManager,
    private val networkManager: NetworkManager
) : PDFRepository {

    private val downloadedData: ((PDFResource, ByteArray) -> Unit)? =
        { resource, responseData -> handleDownloadedData(resource, responseData) }

    private var transferResponse: ((PDFResource) -> Unit)? = null

    override fun getAllDatabaseEntries(): List<PDFResource> = dbManager.getAllDatabaseEntries()

    override fun updateOneItem(res: PDFResource, response: ((PDFResource) -> Unit)?): Boolean {
        transferResponse = response
        val isFilePDFExists = fileManager.fileIsExists("${BuildConfig.FILE_DIR_PATH}${res.fileName}")
        return if (isFilePDFExists) {
            deletePDFFile(res)
            !isFilePDFExists
        } else {
            createPDFFile(res)
        }
    }

    override fun filePDFForShowingIsExists(filePath: String): Boolean = fileManager.fileIsExists(filePath)

    override fun getObservable(): Observable<PDFResource, Int, Observer<PDFResource, Int>> =
        networkManager.getProgressObservable()

    private fun deletePDFFile(res: PDFResource) {
        deleteFile(res)
        deleteThumbnail(res)
        updateItem(res, FILE_IS_NOT_EXISTS)
    }

    private fun deleteFile(res: PDFResource) {
        val filePath = "${res.fileDirPath}${res.fileName}"
        fileManager.deleteFile(filePath)
    }

    private fun deleteThumbnail(res: PDFResource) {
        val imageFilePath = "${res.imageDirPath}${res.imageName}"
        fileManager.deleteFile(imageFilePath)
    }

    private fun createPDFFile(res: PDFResource) =
        networkManager.startDownload(res, downloadedData)

    private fun handleDownloadedData(res: PDFResource, data: ByteArray) {
        writeFile(res, data)
        val imageData = createThumbnail(res)
        writeThumbnail(res, imageData)
        updateItem(res, FILE_IS_EXISTS)
    }

    private fun writeFile(res: PDFResource, data: ByteArray) {
        val isDirExists = fileManager.dirIsExists(res.fileDirPath)
        if (isDirExists) {
            val filePath = "${res.fileDirPath}${res.fileName}"
            fileManager.writeFile(filePath, data)
        } else {
            throw Exception(appContext.getString(R.string.file_dir_is_not_exists))
        }
    }

    private fun writeThumbnail(res: PDFResource, imageData: ByteArray) {
        val isDirExists = fileManager.dirIsExists(res.imageDirPath)
        if (isDirExists) {
            val imageFilePath = "${res.imageDirPath}${res.fileName}$IMAGE_POSTFIX"
            fileManager.writeFile(imageFilePath, imageData)
        } else {
            throw Exception(appContext.getString(R.string.thumbnail_dir_is_not_exists))
        }
    }

    private fun createThumbnail(res: PDFResource): ByteArray {
        val filePath = "${res.fileDirPath}${res.fileName}"
        val data = fileManager.readFile(filePath)
        val pDFCore = PdfiumCore(appContext)
        val pDFDocument = pDFCore.newDocument(data)
        pDFCore.openPage(pDFDocument, PAGE_FOR_THUMBNAIL_NUMBER)
        val pageWidth = pDFCore.getPageWidthPoint(pDFDocument, PAGE_FOR_THUMBNAIL_NUMBER)
        val pageHeight = pDFCore.getPageHeightPoint(pDFDocument, PAGE_FOR_THUMBNAIL_NUMBER)
        val bitmap = Bitmap.createBitmap(pageWidth, pageHeight, Bitmap.Config.ARGB_8888)
        pDFCore.renderPageBitmap(
            pDFDocument,
            bitmap,
            PAGE_FOR_THUMBNAIL_NUMBER,
            PAGE_RENDERING_START_X,
            PAGE_RENDERING_START_Y,
            pageWidth,
            pageHeight
        )
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, IMAGE_QUALITY_IN_PERCENTS, stream)
        val byteArray = stream.toByteArray()
        bitmap.recycle()
        pDFCore.closeDocument(pDFDocument)
        return byteArray
    }

    private fun updateItem(res: PDFResource, fileIsExists: Boolean) {
        val updatedRes = dbManager.updateEntry(res, fileIsExists)
        transferResponse?.invoke(updatedRes)
    }

    companion object {
        const val FILE_IS_EXISTS = true
        const val FILE_IS_NOT_EXISTS = false

        private const val PAGE_FOR_THUMBNAIL_NUMBER = 0
        private const val PAGE_RENDERING_START_X = 0
        private const val PAGE_RENDERING_START_Y = 0
        private const val IMAGE_QUALITY_IN_PERCENTS = 100
    }
}