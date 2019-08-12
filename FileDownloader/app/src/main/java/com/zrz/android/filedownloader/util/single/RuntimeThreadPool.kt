package com.zrz.android.filedownloader.util.single

object RuntimeThreadPool {
    private const val MIN_RUNTIME_THREAD_POOL_SIZE = 3

    fun getRuntimeThreadPoolSize() =
        if (Runtime.getRuntime().availableProcessors() > 3) {
            Runtime.getRuntime().availableProcessors()
        } else {
            MIN_RUNTIME_THREAD_POOL_SIZE
        }
}