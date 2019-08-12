package com.zrz.android.filedownloader.core.mvp

interface MVPPresenter<V : MVPView> {
    var view: V?

    fun attach(view: V) {
        this.view = view
    }

    fun reportError(e: Throwable) {
        view?.handleError(e)
    }

    fun showToast(text: String) {
        view?.showToast(text)
    }

    fun detach() {
        this.view = null
    }

    fun isAttach() = this.view != null
}