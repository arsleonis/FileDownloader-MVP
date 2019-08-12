package com.zrz.android.filedownloader.util.extention

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.zrz.android.filedownloader.R

fun ViewGroup.inflateView(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun <VR> AppCompatImageView.showImage(viewRes: VR, errorView: Int = R.mipmap.error) =
    Glide.with(this).load(viewRes).error(errorView).into(this)