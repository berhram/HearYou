package com.velvet.hearyou

import android.content.Context
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.database.DatabaseProvider
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.NoOpCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.offline.DownloadHelper
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadService
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.util.UUID
import java.util.concurrent.Executors

@OptIn(UnstableApi::class)
class AppContainer(private val context: Context) {

    var dataSource = DefaultHttpDataSource.Factory()
        .setUserAgent(Util.getUserAgent(context, context.resources.getString(R.string.app_name)))
        .setConnectTimeoutMs(30 * 1000).setReadTimeoutMs(30 * 1000)
        .setAllowCrossProtocolRedirects(true)

    private var dataBase: DatabaseProvider = StandaloneDatabaseProvider(context)
    private var downloadContentDirectory: File = File(context.getExternalFilesDir(null), "Hear You")
    var downloadCache: Cache =
        SimpleCache(downloadContentDirectory, NoOpCacheEvictor(), dataBase)
    var downloadManager: DownloadManager =
        DownloadManager(
            context,
            dataBase,
            downloadCache,
            dataSource,
            Executors.newSingleThreadExecutor()
        )


}