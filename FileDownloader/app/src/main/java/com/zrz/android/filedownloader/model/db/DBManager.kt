package com.zrz.android.filedownloader.model.db

import com.zrz.android.filedownloader.core.base.manager.FDManager
import com.zrz.android.filedownloader.entity.PDFResource

interface DBManager : FDManager {

    fun getAllDatabaseEntries(): List<PDFResource>

    fun updateEntry(pDFResource: PDFResource, isExists: Boolean): PDFResource
}