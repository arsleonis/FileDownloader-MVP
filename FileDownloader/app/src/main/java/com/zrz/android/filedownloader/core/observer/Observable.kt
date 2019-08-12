package com.zrz.android.filedownloader.core.observer

interface Observable<I, D, in O : Observer<I, D>> {

    fun subscribe(observer: O)

    fun unsubscribe(observer: O)

    fun isSubscribed(observer: O): Boolean

    fun notifyObserversData(identifier: I, data: D)

    fun notifyObserversError(throwable: Throwable)
}