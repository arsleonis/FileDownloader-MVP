package com.zrz.android.filedownloader.model.presenter

import com.zrz.android.filedownloader.core.mvp.MVPPresenter
import com.zrz.android.filedownloader.core.mvp.MVPView

interface PresenterFactory<V : MVPView, P : MVPPresenter<V>> {

    fun createPresenter(): P
}