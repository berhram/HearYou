package com.velvet.hearyou.presentation.main

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import com.velvet.hearyou.App
import com.velvet.hearyou.R
import com.velvet.hearyou.presentation.speech.SpeechRecognitionState
import kotlin.math.roundToInt

const val VIDEO_URL = "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

@OptIn(UnstableApi::class)
@Composable
fun MainScreen(
    state: MainState,
    viewModel: MainViewModel,
    downloadMedia: (String) -> Unit,
    downloadProgress: Float
) {

    Surface(color = MaterialTheme.colors.surface) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                val context = LocalContext.current
                val dataSourceFactory = (context.applicationContext as App).appContainer.dataSource
                val cachedDataSourceFactory = CacheDataSource.Factory()
                    .setCache((context.applicationContext as App).appContainer.downloadCache)
                    .setUpstreamDataSourceFactory(dataSourceFactory)
                val mediaItem = MediaItem.fromUri(Uri.parse(VIDEO_URL))
                val mediaSourceFactory = DefaultMediaSourceFactory(cachedDataSourceFactory)
                val mediaSource = mediaSourceFactory.createMediaSource(mediaItem)
                val exoPlayer = remember {
                    ExoPlayer.Builder(context).setMediaSourceFactory(mediaSourceFactory).build()
                        .apply {
                            setMediaSource(mediaSource)
                            prepare()
                        }
                }
                AndroidView(
                    factory = {
                        PlayerView(it).apply {
                            player = exoPlayer
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text(
                text = "${stringResource(id = R.string.progress)}: ${downloadProgress.roundToInt()}%",
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { downloadMedia(VIDEO_URL) },
                enabled = downloadProgress < 100f
            ) {
                Text(text = stringResource(id = R.string.download))
            }

            val scrollState = rememberScrollState()
            LaunchedEffect(state.convertedText) {
                scrollState.animateScrollTo(scrollState.maxValue)
            }
            Column(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                Text(text = state.convertedText)
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                if (!state.isPermissionGranted) {
                    Text(text = stringResource(id = R.string.please_grant_persmission))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.grantPermission() }) {
                        Text(text = stringResource(id = R.string.grant_permission))
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${stringResource(id = R.string.recording)}:\n${stringResource(id = if (state.isRecording) R.string.recording else R.string.not_recording)}",
                        color = if (state.isRecording) Color.Green else Color.Red
                    )
                    Text(
                        text = "${stringResource(id = R.string.state)}:\n${stringResource(state.speechRecognitionState.message)}"
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.clearButtonClick() },
                        enabled = state.convertedText.isNotEmpty()
                    ) {
                        Text(text = stringResource(id = R.string.clear))
                    }
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.startStopRecording() },
                        enabled = state.isPermissionGranted && state.speechRecognitionState == SpeechRecognitionState.READY
                    ) {
                        Text(text = stringResource(id = if (state.isRecording) R.string.stop else R.string.start))
                    }
                }

            }
        }
    }
}