package com.velvet.hearyou

import android.app.Notification
import android.content.Context
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadNotificationHelper
import androidx.media3.exoplayer.offline.DownloadService
import androidx.media3.exoplayer.scheduler.Scheduler
import java.lang.Exception

@OptIn(UnstableApi::class)
class MyDownloadService : DownloadService(
    1,
    DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL,
    "Hear You",
    R.string.app_name,
    R.string.app_name
) {

    private lateinit var notificationHelper: DownloadNotificationHelper

    override fun onCreate() {
        super.onCreate()
        notificationHelper = DownloadNotificationHelper(this, "Hear You")
        Log.d("DownloadService", "onCreate")
    }

    override fun getDownloadManager(): DownloadManager {
        val manager = (application as App).appContainer.downloadManager
        manager.addListener(object : DownloadManager.Listener {
            override fun onIdle(downloadManager: DownloadManager) {
                super.onIdle(downloadManager)
                Log.d("DownloadService", "Download idle")
            }

            override fun onWaitingForRequirementsChanged(
                downloadManager: DownloadManager,
                waitingForRequirements: Boolean
            ) {
                super.onWaitingForRequirementsChanged(downloadManager, waitingForRequirements)
                Log.d("DownloadService", "onWaitingForRequirementsChanged")
            }

            override fun onDownloadRemoved(downloadManager: DownloadManager, download: Download) {
                Toast.makeText(this@MyDownloadService, "Deleted", Toast.LENGTH_SHORT).show()
            }

            override fun onDownloadsPausedChanged(
                downloadManager: DownloadManager,
                downloadsPaused: Boolean
            ) {
                if (downloadsPaused) {
                    Toast.makeText(this@MyDownloadService, "Paused", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MyDownloadService, "Resumed", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onDownloadChanged(
                downloadManager: DownloadManager,
                download: Download,
                finalException: Exception?
            ) {
                Log.d("DownloadService", "Download ${download.request.id} is in state ${download.state}")
                if (finalException != null) {
                    Log.e("DownloadService", "Download ${download.request.id} failed with exception", finalException)
                }
            }
        })
        return manager
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DownloadService", "onDestroy")
    }

    override fun getForegroundNotification(
        downloads: MutableList<Download>,
        notMetRequirements: Int
    ): Notification {
        return notificationHelper.buildProgressNotification(
            this,
            R.drawable.ic_download,
            null,
            getString(R.string.app_name),
            downloads,
            notMetRequirements
        )
    }

    override fun getScheduler(): Scheduler? {
        return null
    }
}