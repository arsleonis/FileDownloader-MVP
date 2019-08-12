package com.zrz.android.filedownloader.model.network.util

import com.zrz.android.filedownloader.core.observer.BaseObservable
import com.zrz.android.filedownloader.core.observer.Observer
import com.zrz.android.filedownloader.entity.PDFResource

object DownloadProgressObservableDelegate : BaseObservable<PDFResource, Int, Observer<PDFResource, Int>>()