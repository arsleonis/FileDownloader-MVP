package com.zrz.android.filedownloader.util.extention

import com.zrz.android.filedownloader.BuildConfig.*

fun String.asBundleKey() = "$APPLICATION_ID$BUNDLE_EXTRA_KEY$this"

fun String.asIntentKey() = "$APPLICATION_ID$INTENT_EXTRA_KEY$this"