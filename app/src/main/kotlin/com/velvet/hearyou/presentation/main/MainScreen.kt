package com.velvet.hearyou.presentation.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.velvet.hearyou.R
import com.velvet.hearyou.presentation.ui.AppTheme
import com.velvet.hearyou.presentation.speech.SpeechRecognitionState

@Composable
fun MainScreen(state: MainState) {
    Surface(color = MaterialTheme.colors.surface) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(text = state.convertedText)
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                if (!state.isPermissionGranted) {
                    Text(text = stringResource(id = R.string.please_grant_persmission))
                    Button(modifier = Modifier.width(250.dp), onClick = { state.onGrantClick() }) {
                        Text(text = stringResource(id = R.string.grant_permission))
                    }
                }
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = "${stringResource(id = R.string.recording)}: ${stringResource(id = if (state.isRecording) R.string.recording else R.string.not_recording)}",
                    color = if (state.isRecording) Color.Green else Color.Red
                )
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = "${stringResource(id = R.string.state)}: ${stringResource(state.speechRecognitionState.message)}"
                )
                Button(
                    modifier = Modifier.width(250.dp),
                    onClick = { state.onClearClick() },
                    enabled = state.convertedText.isNotEmpty()
                ) {
                    Text(text = stringResource(id = R.string.clear))
                }
                Button(
                    modifier = Modifier.width(250.dp),
                    onClick = { state.onStartStopClick() },
                    enabled = state.isPermissionGranted && state.speechRecognitionState == SpeechRecognitionState.READY
                ) {
                    Text(text = stringResource(id = if (state.isRecording) R.string.stop else R.string.start))
                }
            }
        }
    }
}

@Preview
@Composable
fun LightThemePreview() {
    AppTheme {
        MainScreen(
            MainState(
                convertedText = "some text that pronounced by me",
                isRecording = true,
                isPermissionGranted = false,
                onGrantClick = {},
                onStartStopClick = {},
                onClearClick = {},
                speechRecognitionState = SpeechRecognitionState.READY
            )
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DarkThemePreview() {
    AppTheme {
        MainScreen(
            MainState(
                convertedText = "some text that pronounced by me",
                isRecording = false,
                isPermissionGranted = true,
                onGrantClick = {},
                onStartStopClick = {},
                onClearClick = {},
                speechRecognitionState = SpeechRecognitionState.ERROR
            )
        )
    }
}