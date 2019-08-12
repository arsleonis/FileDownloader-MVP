package com.zrz.android.filedownloader.model.network

import com.zrz.android.filedownloader.core.base.manager.FDManager
import com.zrz.android.filedownloader.core.observer.Observable
import com.zrz.android.filedownloader.core.observer.Observer
import com.zrz.android.filedownloader.entity.PDFResource

interface NetworkManager : FDManager {

    fun startDownload(res: PDFResource, downloadedData: ((PDFResource, ByteArray) -> Unit)?): Boolean

    fun downloadFile(res: PDFResource): ByteArray

    fun completeDownload(res: PDFResource, id: Int, downloadedData: ByteArray)

    fun restartDownload()

    fun notifyError(throwable: Throwable)

    fun getProgressObservable(): Observable<PDFResource, Int, Observer<PDFResource, Int>>
}