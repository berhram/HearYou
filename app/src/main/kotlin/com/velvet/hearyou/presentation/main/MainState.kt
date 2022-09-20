package com.velvet.hearyou.presentation.main

import androidx.compose.runtime.Immutable
import com.velvet.hearyou.speech.SpeechRecognitionState

@Immutable
data class MainState(
    val convertedText: String = "",
    val isRecording: Boolean = false,
    val isPermissionGranted: Boolean,
    val onGrantClick: () -> Unit,
    val onStartStopClick: () -> Unit,
    val speechRecognitionState: SpeechRecognitionState = SpeechRecognitionState.NONE,
    val onClearClick: () -> Unit
)
