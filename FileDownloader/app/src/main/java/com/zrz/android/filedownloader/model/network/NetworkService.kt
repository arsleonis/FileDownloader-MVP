package com.zrz.android.filedownloader.model.network

import android.os.Parcelable
import com.zrz.android.filedownloader.core.base.service.BaseNetworkService
import com.zrz.android.filedownloader.entity.PDFResource

class NetworkService : BaseNetworkService() {

    override fun getNotificationText(resource: Parcelable): String = (resource as PDFResource).urlAddress

    override fun startServiceInBackground(resource: Parcelable, id: Int) {
        doAsyncDownload(resource as PDFResource, id) { res, id_, response ->
            networkManager.completeDownload(res, id_, response)
        }
    }

    override fun onDestroy() {
        networkManager.restartDownload()
        super.onDestroy()
    }
}