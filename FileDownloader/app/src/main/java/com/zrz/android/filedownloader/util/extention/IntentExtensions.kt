package com.zrz.android.filedownloader.util.extention

import android.content.Intent
import android.os.Parcelable

fun Intent.getStartCommand(intentKey: String): String =
    getBundleExtra(intentKey.asIntentKey())?.getString(intentKey.asBundleKey())!!

fun Intent.getId(intentKey: String, bundleKey: String): Int =
    getBundleExtra(intentKey.asIntentKey())?.getInt(bundleKey.asBundleKey())!!

fun <I : Parcelable> Intent.getResources(intentKey: String, bundleKey: String): I =
    getBundleExtra(intentKey.asIntentKey())?.getParcelable(bundleKey.asBundleKey())!!