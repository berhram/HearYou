package com.velvet.hearyou.presentation

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.DownloadHelper
import androidx.media3.exoplayer.offline.DownloadService
import com.velvet.hearyou.App
import com.velvet.hearyou.MyDownloadService
import com.velvet.hearyou.presentation.main.MainScreen
import com.velvet.hearyou.presentation.main.MainViewModel
import com.velvet.hearyou.presentation.permission.ManagePermission
import com.velvet.hearyou.presentation.speech.PermissionListener
import com.velvet.hearyou.presentation.ui.AppTheme
import com.velvet.hearyou.presentation.ui.SystemUISetup
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import java.io.IOException
import java.util.UUID


@OptIn(UnstableApi::class)
class AppActivity : ComponentActivity(), PermissionListener {

    private val permissionCache: ManagePermission by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContent {
            AppTheme {
                SystemUISetup()
                val viewModel = koinViewModel<MainViewModel>()
                val state = viewModel.collectAsState().value
                var downloadProgress by remember { mutableFloatStateOf(0f) }
                LaunchedEffect(Unit) {
                    while (downloadProgress < 100) {
                        downloadProgress = downloadProgress()
                        delay(250)
                    }
                }
                MainScreen(
                    state, viewModel,
                    downloadMedia = {
                        downloadMedia(it)
                    },
                    downloadProgress = downloadProgress
                )
            }
        }
        permissionCache.setListener(this)
    }

    override fun checkPermission(permission: String): Boolean =
        checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

    override fun requirePermission(permission: String) = requestPermissions(arrayOf(permission), 0)

    private fun resumeDownload() {
        DownloadService.sendResumeDownloads(
            this, MyDownloadService::class.java, true
        )
    }

    private fun pauseDownload() {
        DownloadService.sendPauseDownloads(this, MyDownloadService::class.java, true)
    }

    private fun downloadProgress(): Float {
        val downloadManager = (application as App).appContainer.downloadManager
        val downloads = downloadManager.currentDownloads
        val download = downloads.getOrNull(0) ?: return 0f
        return download.percentDownloaded
    }


    private fun downloadMedia(uri: String) {

        val helper = DownloadHelper.forMediaItem(this, MediaItem.fromUri(uri))
        helper.prepare(object : DownloadHelper.Callback {
            override fun onPrepared(helper: DownloadHelper) {
                val download = helper.getDownloadRequest(
                    UUID.randomUUID().toString(), null
                )
                DownloadService.sendAddDownload(
                    this@AppActivity, MyDownloadService::class.java, download, true
                )
            }

            override fun onPrepareError(helper: DownloadHelper, e: IOException) {
                e.printStackTrace()
                Toast.makeText(this@AppActivity, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}