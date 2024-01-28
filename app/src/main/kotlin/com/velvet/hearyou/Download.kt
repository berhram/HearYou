package com.velvet.hearyou

import android.content.Context
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import okio.buffer
import okio.sink

fun filePathInInternalStorage(context: Context, fileName: String): String {
    return context.filesDir.absolutePath + "/" + fileName
}

fun downloadFileInInternalStorage(context: Context, link: String, fileName: String, progressCallback: (Long, Long) -> Unit) {
    val request = Request.Builder()
        .url(link)
        .build()
    val client = OkHttpClient.Builder()
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            val responseBody = response.body
            if (responseBody != null) {
                try {
                    context.openFileOutput(fileName, Context.MODE_PRIVATE).use { output ->
                        val sink = output.sink().buffer()
                        val source = responseBody.source()

                        val total = responseBody.contentLength()
                        var totalRead: Long = 0

                        val buffer = ByteArray(8 * 1024)
                        var read: Long

                        while (source.read(buffer).also { read = it.toLong() } != -1) {
                            sink.write(buffer, 0, read.toInt())
                            totalRead += read
                            progressCallback(totalRead / (1024 * 1024), total / (1024 * 1024))
                        }

                        sink.flush()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }
    })
}