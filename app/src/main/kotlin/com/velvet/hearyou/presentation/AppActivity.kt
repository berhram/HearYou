package com.velvet.hearyou.presentation

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.activity.compose.setContent
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeComponentActivity
import com.velvet.hearyou.presentation.nav.RootNode
import com.velvet.hearyou.presentation.ui.AppTheme
import com.velvet.hearyou.presentation.ui.SystemUISetup
import java.util.Locale

/**
 * ACTION_RECOGNIZE_SPEECH:
 * Starts an activity that will prompt the user for speech and send it through a speech recognizer.
 * EXTRA_LANGUAGE_MODEL:
 * Informs the recognizer which speech model to prefer when performing ACTION_RECOGNIZE_SPEECH.
 * LANGUAGE_MODEL_FREE_FORM:
 * Use a language model based on free-form speech recognition.
 * EXTRA_LANGUAGE:
 * Optional IETF language tag (as defined by BCP 47), for example, “en-US”.
 */
class AppActivity : NodeComponentActivity(), ManagePermission, RecognitionListener,
    SpeechRecognition {

    private val speechRecognizer =
        SpeechRecognizer.createSpeechRecognizer(this)

    private val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

    private var recognizedData: String = ""

    init {
        speechIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        speechRecognizer.setRecognitionListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContent {
            AppTheme {
                SystemUISetup()
                NodeHost(integrationPoint = integrationPoint) {
                    RootNode(buildContext = it, managePermission = this)
                }
            }
        }
    }

    override fun checkPermission(permission: String): Boolean =
        checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

    override fun requirePermission(permission: String): Boolean {
        requestPermissions(arrayOf(permission), 0)
        return checkPermission(permission)
    }

    override fun onReadyForSpeech(p0: Bundle?) {

    }

    override fun onBeginningOfSpeech() {

    }

    override fun onRmsChanged(p0: Float) {

    }

    override fun onBufferReceived(p0: ByteArray?) {

    }

    override fun onEndOfSpeech() {

    }

    override fun onError(p0: Int) {

    }

    override fun onPartialResults(p0: Bundle?) {

    }

    override fun onEvent(p0: Int, p1: Bundle?) {

    }

    override fun onResults(p0: Bundle?) {
        recognizedData = p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.get(0) ?: ""
    }

    override fun startRecognition() {
        speechRecognizer.startListening(speechIntent)
    }

    override fun stopRecognition(): String {
        speechRecognizer.stopListening()
        return recognizedData
    }
}