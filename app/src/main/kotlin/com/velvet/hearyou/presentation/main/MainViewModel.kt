package com.velvet.hearyou.presentation.main

import android.Manifest
import androidx.lifecycle.ViewModel
import com.velvet.hearyou.presentation.ManagePermission
import com.velvet.hearyou.presentation.SpeechRecognition
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class MainViewModel(
    private val managePermission: ManagePermission,
    private val speechRecognition: SpeechRecognition
) : ViewModel(), ContainerHost<MainState, MainEffect> {

    override val container: Container<MainState, MainEffect> =
        container(MainState(onGrantClick = { grantPermission() }, onStartStopClick = {}, isPermissionGranted = managePermission.checkPermission(Manifest.permission.RECORD_AUDIO)))

    private fun grantPermission() = managePermission.requirePermission(Manifest.permission.RECORD_AUDIO)

    private fun startStopRecording() = intent {
        if (state.isRecording) {
            reduce { state.copy(convertedText = speechRecognition.stopRecognition()) }
        } else {
            speechRecognition.startRecognition()
        }
        reduce { state.copy(isRecording = !state.isRecording) }
    }
}