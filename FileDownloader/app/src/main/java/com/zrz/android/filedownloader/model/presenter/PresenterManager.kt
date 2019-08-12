package com.zrz.android.filedownloader.model.presenter

import com.zrz.android.filedownloader.core.base.manager.FDManager
import com.zrz.android.filedownloader.core.mvp.MVPPresenter
import com.zrz.android.filedownloader.core.mvp.MVPView

interface PresenterManager : FDManager {

    fun <V : MVPView, P : MVPPresenter<V>> addPresenter(tag: String, factory: PresenterFactory<V, P>): P

    fun removePresenter(tag: String)
}