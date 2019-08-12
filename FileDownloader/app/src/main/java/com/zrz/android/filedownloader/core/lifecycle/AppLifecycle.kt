package com.zrz.android.filedownloader.core.lifecycle

import androidx.lifecycle.ProcessLifecycleOwner

object AppLifecycle {
    val appLifecycle = ProcessLifecycleOwner.get().lifecycle
}