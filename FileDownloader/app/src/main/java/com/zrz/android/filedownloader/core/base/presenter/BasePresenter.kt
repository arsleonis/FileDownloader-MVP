package com.zrz.android.filedownloader.core.base.presenter

import android.content.Context
import android.content.res.Resources
import androidx.annotation.StringRes
import com.zrz.android.filedownloader.core.App
import com.zrz.android.filedownloader.core.di.AppInjectionAsync
import com.zrz.android.filedownloader.core.mvp.MVPPresenter
import com.zrz.android.filedownloader.core.mvp.MVPView

abstract class BasePresenter<V : MVPView>(appContext: Context) : MVPPresenter<V> {

    override var view: V? = null

    private val resources: Resources? = (appContext as App).resources

    val executor = AppInjectionAsync.executors
    val handler = AppInjectionAsync.handler

    fun strings(@StringRes id: Int): String = resources!!.getString(id)

    inline fun doAsyncAction(crossinline action: () -> Unit, vararg results: (() -> Unit)) {
        executor.execute {
            try {
                action.invoke()
                handler.post {
                    val actionsList = listOf(*results)
                    actionsList.forEach { it.invoke() }
                }
            } catch (throwable: Throwable) {
                handler.post {
                    reportError(throwable)
                }
            }
        }
    }

    inline fun <T> doAsyncActionWithResponse(crossinline action: () -> T, vararg results: (T) -> Unit) {
        executor.execute {
            try {
                val response = action.invoke()
                handler.post {
                    val resultsList = listOf(*results)
                    resultsList.forEach { it.invoke(response) }
                }
            } catch (throwable: Throwable) {
                handler.post {
                    reportError(throwable)
                }
            }
        }
    }
}