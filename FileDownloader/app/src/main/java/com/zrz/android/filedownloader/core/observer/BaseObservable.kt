package com.zrz.android.filedownloader.core.observer

open class BaseObservable<I, D, in O : Observer<I, D>> : Observable<I, D, O> {
    private val observers: ArrayList<Observer<I, D>> = ArrayList()

    override fun subscribe(observer: O) {
        observers.add(observer)
    }

    override fun unsubscribe(observer: O) {
        observers.remove(observer)
    }

    override fun isSubscribed(observer: O): Boolean =
        observers.any { true }

    override fun notifyObserversData(identifier: I, data: D) {
        observers.forEach { it.onObserveData(identifier, data) }
    }

    override fun notifyObserversError(throwable: Throwable) {
        observers.forEach { it.onObserveError(throwable) }
    }
}