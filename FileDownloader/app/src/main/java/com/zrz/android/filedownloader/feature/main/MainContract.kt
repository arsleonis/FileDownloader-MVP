package com.zrz.android.filedownloader.feature.main

import com.zrz.android.filedownloader.core.mvp.MVPPresenter
import com.zrz.android.filedownloader.core.mvp.MVPView
import com.zrz.android.filedownloader.entity.PDFResource

interface Presenter : MVPPresenter<View> {

    fun requestPDFListData()

    fun requestUpdateOneItem(res: PDFResource)

    fun startShowPDF(fileName: String)
}

interface View : MVPView {

    fun showList(resList: ArrayList<PDFResource>)

    fun updateItem(res: PDFResource)

    fun updateItemProgress(resource: PDFResource, progress: Int)

    fun showPDF(filePath: String)
}