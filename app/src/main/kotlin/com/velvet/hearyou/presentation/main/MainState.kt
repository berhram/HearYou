package com.velvet.hearyou.presentation.main

import androidx.compose.runtime.Immutable
import com.velvet.hearyou.presentation.speech.SpeechRecognitionState

@Immutable
data class MainState(
    val convertedText: String = "",
    val isRecording: Boolean = false,
    val isPermissionGranted: Boolean = false,
    val speechRecognitionState: SpeechRecognitionState = SpeechRecognitionState.NONE
)
