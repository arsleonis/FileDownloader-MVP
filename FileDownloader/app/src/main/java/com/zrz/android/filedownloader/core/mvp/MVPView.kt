package com.zrz.android.filedownloader.core.mvp

interface MVPView {

    fun handleError(e: Throwable)

    fun showToast(text: String, gravity: Int = 17)
}