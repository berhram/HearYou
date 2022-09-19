package com.velvet.hearyou.presentation.main

import android.Manifest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velvet.hearyou.presentation.ManagePermission
import com.velvet.hearyou.presentation.SpeechRecognition
import com.velvet.hearyou.presentation.SpeechRecognitionListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class MainViewModel(
    private val managePermission: ManagePermission,
    private val speechRecognition: SpeechRecognition
) : ViewModel(), ContainerHost<MainState, MainEffect>, SpeechRecognitionListener {

    override val container: Container<MainState, MainEffect> =
        container(
            MainState(
                onGrantClick = { grantPermission() },
                onStartStopClick = { startStopRecording() },
                isPermissionGranted = false
            )
        )

    init {
        speechRecognition.setSpeechRecognitionListener(this)
    }

    override fun onSpeechRecognized(data: String) = intent {
        reduce {
            state.copy(convertedText = "${state.convertedText} $data")
        }
    }

    private fun grantPermission() = intent {
        val isPermissionGranted =
            managePermission.requirePermission(Manifest.permission.RECORD_AUDIO)
        reduce {
            state.copy(
                isPermissionGranted = isPermissionGranted
            )
        }
    }

    private fun startStopRecording() = intent {
        if (state.isRecording) {
            viewModelScope.launch(Dispatchers.Main) {
                speechRecognition.stopRecognition()
            }
        } else {
            reduce { state.copy(convertedText = "") }
            viewModelScope.launch(Dispatchers.Main) {
                speechRecognition.startRecognition()
            }
        }
        reduce { state.copy(isRecording = !state.isRecording) }
    }
}