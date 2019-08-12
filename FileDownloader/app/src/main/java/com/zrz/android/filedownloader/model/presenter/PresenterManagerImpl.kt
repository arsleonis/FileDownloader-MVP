package com.zrz.android.filedownloader.model.presenter

import androidx.collection.ArrayMap
import com.zrz.android.filedownloader.core.mvp.MVPPresenter
import com.zrz.android.filedownloader.core.mvp.MVPView

class PresenterManagerImpl : PresenterManager {
    private val presenters = ArrayMap<String, MVPPresenter<*>>()

    @Suppress("UNCHECKED_CAST")
    override fun <V : MVPView, P : MVPPresenter<V>> addPresenter(
        tag: String,
        factory: PresenterFactory<V, P>
    ): P = if (presenters[tag] != null) {
        presenters[tag] as P
    } else {
        val newPresenter = factory.createPresenter()
        presenters[tag] = newPresenter
        newPresenter
    }

    override fun removePresenter(tag: String) {
        presenters.remove(tag)
    }
}