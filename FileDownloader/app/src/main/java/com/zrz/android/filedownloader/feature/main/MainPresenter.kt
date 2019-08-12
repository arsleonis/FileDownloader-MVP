package com.zrz.android.filedownloader.feature.main

import android.content.Context
import com.zrz.android.filedownloader.BuildConfig.FILE_DIR_PATH
import com.zrz.android.filedownloader.BuildConfig.NO_IMAGE
import com.zrz.android.filedownloader.R
import com.zrz.android.filedownloader.core.base.presenter.BasePresenter
import com.zrz.android.filedownloader.core.di.AppInjectionRepository
import com.zrz.android.filedownloader.core.observer.Observable
import com.zrz.android.filedownloader.core.observer.Observer
import com.zrz.android.filedownloader.entity.PDFResource
import com.zrz.android.filedownloader.repository.pdf.PDFRepository

class MainPresenter(appContext: Context) :
    BasePresenter<View>(appContext),
    Presenter,
    Observer<PDFResource, Int> {

    private val pDFRepository = AppInjectionRepository.getRequiredRepository<PDFRepository>(appContext)
    private val response: ((PDFResource) -> Unit)? = { resource -> handleResponse(resource) }

    private lateinit var observable: Observable<PDFResource, Int, Observer<PDFResource, Int>>

    override fun onObserveData(identifier: PDFResource, data: Int) {
        handler.post {
            view?.updateItemProgress(identifier, data)
        }
    }

    override fun onObserveError(throwable: Throwable) {
        handler.post {
            reportError(throwable)
        }
    }

    override fun requestPDFListData() {
        doAsyncActionWithResponse(
            { pDFRepository.getAllDatabaseEntries() as ArrayList<PDFResource> },
            { showPDFList(it) })
    }

    override fun requestUpdateOneItem(res: PDFResource) {
        subscribeObservable()
        doAsyncActionWithResponse(
            { pDFRepository.updateOneItem(res, response) },
            { if (it) showToast(strings(R.string.file_start_downloading)) })
    }

    private fun subscribeObservable() {
        observable = pDFRepository.getObservable()
        observable.subscribe(this)
    }

    private fun unsubscribeObservable() {
        observable.unsubscribe(this)
    }

    private fun handleResponse(res: PDFResource) {
        val toastText =
            if (res.imageName == NO_IMAGE) strings(R.string.file_deleted) else strings(R.string.file_downloaded)
        updateItem(res, toastText)
    }

    private fun updateItem(res: PDFResource, toastText: String) {
        handler.post {
            unsubscribeObservable()
            showToast(toastText)
            view?.updateItem(res)
        }
    }

    override fun startShowPDF(fileName: String) {
        val filePath = "$FILE_DIR_PATH$fileName"
        if (pDFRepository.filePDFForShowingIsExists(filePath)) {
            view?.showPDF(filePath)
        } else {
            view?.showToast(strings(R.string.file_is_not_downloaded))
        }
    }

    private fun showPDFList(resList: ArrayList<PDFResource>) {
        view?.showList(resList)
    }
}