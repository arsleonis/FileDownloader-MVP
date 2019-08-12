package com.zrz.android.filedownloader.model.network

import android.os.Parcelable
import com.zrz.android.filedownloader.core.base.service.BaseNetworkService
import com.zrz.android.filedownloader.entity.PDFResource

class NetworkService : BaseNetworkService() {

    override fun getNotificationText(res: Parcelable): String = (res as PDFResource).urlAddress

    override fun startServiceInBackground(res: Parcelable, id: Int) {
        doAsyncDownload(res as PDFResource, id) { res_, id_, response ->
            networkManager.completeDownload(res_, id_, response)
        }
    }

    override fun onDestroy() {
        networkManager.restartDownload()
        super.onDestroy()
    }
}