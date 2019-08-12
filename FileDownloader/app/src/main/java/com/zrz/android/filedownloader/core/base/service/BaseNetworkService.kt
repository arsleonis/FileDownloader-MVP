package com.zrz.android.filedownloader.core.base.service

import android.os.Handler
import com.zrz.android.filedownloader.core.di.AppInjectionAsync
import com.zrz.android.filedownloader.core.di.AppInjectionManager
import com.zrz.android.filedownloader.entity.PDFResource
import com.zrz.android.filedownloader.model.network.NetworkManager
import java.util.concurrent.ExecutorService

abstract class BaseNetworkService : BaseService() {
    lateinit var executor: ExecutorService
    lateinit var handler: Handler
    lateinit var networkManager: NetworkManager

    override fun onCreate() {
        super.onCreate()
        executor = AppInjectionAsync.executors
        handler = AppInjectionAsync.handler
        networkManager = AppInjectionManager.getRequiredManager()
    }

    inline fun doAsyncDownload(
        resource: PDFResource,
        resourceId: Int,
        crossinline handlingResponse: (PDFResource, Int, ByteArray) -> Unit
    ) {
        executor.execute {
            try {
                val responseData = networkManager.downloadFile(resource)
                handlingResponse.invoke(resource, resourceId, responseData)
            } catch (throwable: Throwable) {
                handler.post {
                    networkManager.notifyError(throwable)
                }
            }
        }
    }
}