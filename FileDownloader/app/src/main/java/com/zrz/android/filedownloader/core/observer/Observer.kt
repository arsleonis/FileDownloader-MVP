package com.zrz.android.filedownloader.core.observer

interface Observer<I, D> {

    fun onObserveData(identifier: I, data: D)

    fun onObserveError(throwable: Throwable)
}