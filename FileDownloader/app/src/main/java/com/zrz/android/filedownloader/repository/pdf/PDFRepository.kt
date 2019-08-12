package com.zrz.android.filedownloader.repository.pdf

import com.zrz.android.filedownloader.core.base.repository.FDRepository
import com.zrz.android.filedownloader.core.observer.Observable
import com.zrz.android.filedownloader.core.observer.Observer
import com.zrz.android.filedownloader.entity.PDFResource

interface PDFRepository : FDRepository {

    fun getAllDatabaseEntries(): List<PDFResource>

    fun updateOneItem(res: PDFResource, response: ((PDFResource) -> Unit)?): Boolean

    fun filePDFForShowingIsExists(filePath: String): Boolean

    fun getObservable(): Observable<PDFResource, Int, Observer<PDFResource, Int>>
}