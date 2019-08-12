package com.zrz.android.filedownloader.model.network.util

import androidx.annotation.IntRange
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*

class DownloadableResponseBody(
    var responseBody: ResponseBody,
    val countProgress: ((Int) -> Unit),
    private var bufferedSource: BufferedSource? = null
) : ResponseBody() {

    override fun contentLength(): Long = responseBody.contentLength()

    override fun contentType(): MediaType? = responseBody.contentType()

    override fun source(): BufferedSource = bufferedSource ?: getSource(responseBody.source()).buffer()

    private fun getSource(source: Source): Source {
        var totalBytesRead: Long = 0

        return object : ForwardingSource(source) {
            private var currentProgress = 0
            private var newProgress = 0
            private var bytesRead = 0L

            override fun read(sink: Buffer, byteCount: Long): Long {
                bytesRead = super.read(sink, byteCount)
                totalBytesRead += if (bytesRead != -1L) bytesRead else 0
                newProgress = (totalBytesRead * 100 / responseBody.contentLength()).toInt()
                if (newProgress != currentProgress && newProgress % progressShowingStep == 0) {
                    countProgress.invoke(newProgress)
                    currentProgress = newProgress
                }
                return bytesRead
            }
        }
    }

    companion object {
        @IntRange(from = 1, to = 100)
        private const val progressShowingStep = 5
    }
}