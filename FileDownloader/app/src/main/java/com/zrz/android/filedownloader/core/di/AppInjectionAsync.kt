package com.zrz.android.filedownloader.core.di

import android.os.Handler
import android.os.Looper
import com.zrz.android.filedownloader.util.single.RuntimeThreadPool
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object AppInjectionAsync {
    lateinit var executors: ExecutorService
    lateinit var handler: Handler

    fun initializeAsyncComponents() {
        executors = Executors.newFixedThreadPool(RuntimeThreadPool.getRuntimeThreadPoolSize())
        handler = Handler(Looper.getMainLooper())
    }
}