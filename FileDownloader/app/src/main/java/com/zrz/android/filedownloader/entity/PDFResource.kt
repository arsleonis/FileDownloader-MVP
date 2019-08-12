package com.zrz.android.filedownloader.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PDFResource(
    val urlAddress: String,
    val fileName: String,
    val fileDirPath: String,
    val imageName: String,
    val imageDirPath: String
) : Parcelable