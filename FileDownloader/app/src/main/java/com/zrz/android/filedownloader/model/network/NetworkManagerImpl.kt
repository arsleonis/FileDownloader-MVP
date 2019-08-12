package com.zrz.android.filedownloader.model.network

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.zrz.android.filedownloader.BuildConfig.*
import com.zrz.android.filedownloader.core.observer.Observable
import com.zrz.android.filedownloader.core.observer.Observer
import com.zrz.android.filedownloader.entity.PDFResource
import com.zrz.android.filedownloader.model.network.util.DownloadProgressObservableDelegate
import com.zrz.android.filedownloader.util.single.Downloading
import com.zrz.android.filedownloader.util.extention.startNewService

class NetworkManagerImpl(
    private val appContext: Context,
    private val lifecycleObservable: Lifecycle,
    private var ordinalNumber: Int = 0
) : NetworkManager,
    Observable<PDFResource, Int, Observer<PDFResource, Int>> by DownloadProgressObservableDelegate,
    LifecycleObserver {

    private val downloads: HashMap<PDFResource, Int> = hashMapOf()

    private var responseData: ((PDFResource, ByteArray) -> Unit)? = null

    override fun startDownload(res: PDFResource, downloadedData: ((PDFResource, ByteArray) -> Unit)?): Boolean {
        if (isInDownloads(res)) return false
        if (responseData == null) responseData = downloadedData
        if (downloads.isEmpty()) lifecycleObservable.addObserver(this)
        startService(res)
        return true
    }

    override fun downloadFile(res: PDFResource): ByteArray {
        val urlAddress = res.urlAddress
        val reportProgress: ((Int) -> Unit) = { progressValue -> notifyObserversData(res, progressValue) }
        return Downloading.downloadFileWithShowingProgress(urlAddress, reportProgress)
    }

    override fun completeDownload(res: PDFResource, id: Int, downloadedData: ByteArray) {
        if (downloads.containsKey(res) && downloads[res] == id) {
            downloads.remove(res)
            if (downloads.isEmpty()) {
                lifecycleObservable.removeObserver(this)
                killService()
            }
            responseData?.invoke(res, downloadedData)
        }
    }

    override fun restartDownload() {
        if (downloads.isNotEmpty()) restartService()
    }

    override fun notifyError(throwable: Throwable) {
        notifyObserversError(throwable)
    }

    override fun getProgressObservable(): Observable<PDFResource, Int, Observer<PDFResource, Int>> = this

    private fun startService(res: PDFResource) {
        val startServiceCommand = getActualStartServiceCommand()
        ordinalNumber++
        downloads[res] = ordinalNumber
        appContext.startNewService<NetworkService>(
            KEY_START_SERVICE_COMMAND to startServiceCommand,
            KEY_ID to ordinalNumber,
            KEY_PDF_RESOURCE to res
        )
    }

    private fun getActualStartServiceCommand() =
        if (lifecycleObservable.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            COMMAND_START_SERVICE_IN_BACKGROUND
        } else {
            COMMAND_START_SERVICE_IN_FOREGROUND
        }

    private fun killService() {
        appContext.startNewService<NetworkService>(
            KEY_START_SERVICE_COMMAND to COMMAND_STOP_SERVICE,
            KEY_ID to ID_DEFAULT_VALUE
        )
    }

    private fun restartService() {
        downloads.forEach { (pdfResource, _) -> downloads[pdfResource] = ++ordinalNumber }
        val startServiceCommand = getActualStartServiceCommand()
        downloads.forEach {
            appContext.startNewService<NetworkService>(
                KEY_START_SERVICE_COMMAND to startServiceCommand,
                KEY_ID to it.value,
                KEY_PDF_RESOURCE to it.key
            )
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun restartDownloadServiceInBackground() {
        if (downloads.isNotEmpty()) {
            killService()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun restartDownloadServiceInForeground() {
        if (downloads.isNotEmpty()) {
            killService()
        }
    }

    private fun isInDownloads(res: PDFResource) =
        downloads.contains(res)
}