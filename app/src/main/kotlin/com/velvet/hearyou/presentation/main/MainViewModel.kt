package com.velvet.hearyou.presentation.main

import android.Manifest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velvet.hearyou.presentation.permission.ManagePermission
import com.velvet.hearyou.presentation.speech.SpeechRecognition
import com.velvet.hearyou.presentation.speech.SpeechRecognitionListener
import com.velvet.hearyou.presentation.speech.SpeechRecognitionState
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
                isPermissionGranted = false,
                onClearClick = { onClearButtonClick() }
            )
        )

    init {
        speechRecognition.setSpeechRecognitionListener(this)
        checkPermission()
    }

    override fun onSpeechRecognized(data: String) = intent {
        reduce {
            state.copy(convertedText = "${state.convertedText}\n$data")
        }
    }

    override fun onState(state: SpeechRecognitionState) = intent {
        reduce {
            this.state.copy(speechRecognitionState = state)
        }
    }

    private fun onClearButtonClick() = intent {
        reduce {
            state.copy(convertedText = "")
        }
    }

    private fun checkPermission() = intent {
        val isPermissionGranted = managePermission.checkPermission(Manifest.permission.RECORD_AUDIO)
        reduce {
            state.copy(isPermissionGranted = isPermissionGranted)
        }
    }

    private fun grantPermission() = intent {
        managePermission.requirePermission(Manifest.permission.RECORD_AUDIO)
        checkPermission()
    }

    private fun startStopRecording() = intent {
        if (state.isRecording) {
            viewModelScope.launch(Dispatchers.Main) {
                speechRecognition.stopRecognition()
            }
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                speechRecognition.startRecognition()
            }
        }
        reduce { state.copy(isRecording = !state.isRecording) }
    }
}