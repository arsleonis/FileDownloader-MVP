package com.zrz.android.filedownloader.util.single

import com.zrz.android.filedownloader.model.network.util.DownloadableResponseBody
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

object Downloading {

    fun downloadFileWithShowingProgress(urlAddress: String, reportProgress: ((Int) -> Unit)): ByteArray {
        val request = Request.Builder()
            .url(urlAddress)
            .build()
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val originalResponse = chain.proceed(chain.request())
                    return originalResponse.newBuilder()
                        .body(
                            DownloadableResponseBody(
                                originalResponse.body!!,
                                reportProgress
                            )
                        )
                        .build()
                }
            })
            .build()
        val response = client.newCall(request).execute()
        return response.body!!.bytes()
    }
}