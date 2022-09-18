package com.velvet.hearyou.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.velvet.hearyou.R

@Composable
fun MainScreen(state: MainState) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            if (!state.isPermissionGranted) {
                Text(text = stringResource(id = R.string.please_grant_persmission))
                Button(onClick = { state.onGrantClick() }) {
                    Text(text = stringResource(id = R.string.grant_permission))
                }
            }
            Text(text = state.convertedText)
            Text(
                text = stringResource(id = if (state.isRecording) R.string.recording else R.string.not_recording),
                color = if (state.isRecording) Color.Green else Color.Red
            )
            Button(onClick = { state.onStartStopClick() }) {
                Text(text = stringResource(id = if (state.isRecording) R.string.stop else R.string.start))
            }
        }
    }
}

@Preview
@Composable
fun LightThemePreview() {
    MainScreen(
        MainState(
            convertedText = "some text that pronounced by me",
            isRecording = true,
            isPermissionGranted = false,
            onGrantClick = {},
            onStartStopClick = {})
    )
}