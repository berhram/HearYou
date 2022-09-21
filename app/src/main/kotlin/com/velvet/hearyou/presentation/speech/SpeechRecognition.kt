package com.velvet.hearyou.presentation.speech

import android.content.Context
import com.google.gson.Gson

import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.vosk.android.StorageService
import java.lang.Exception

interface SpeechRecognition {

    fun startRecognition()

    fun stopRecognition()

    fun setSpeechRecognitionListener(listener: SpeechRecognitionListener)

    class Base(private val context: Context) : SpeechRecognition, RecognitionListener {

        private val gson = Gson()

        private var speechListener: SpeechRecognitionListener? = null

        private var model: Model? = null

        private var speechService: SpeechService? = null

        override fun onPartialResult(hypothesis: String) = Unit

        override fun onResult(hypothesis: String) {
            val text = gson.fromJson(hypothesis, Text::class.java).text
            if (text.isNotBlank())
                speechListener?.onSpeechRecognized(text)
        }

        override fun onFinalResult(hypothesis: String) = Unit

        override fun onError(exception: Exception) {
            onStateChanged(SpeechRecognitionState.ERROR)
        }

        override fun onTimeout() = Unit

        override fun startRecognition() {
            val recognizer = Recognizer(model, 16000f)
            speechService = SpeechService(recognizer, 16000f)
            speechService?.startListening(this)
        }

        override fun stopRecognition() {
            speechService?.stop()
            speechService = null
        }

        override fun setSpeechRecognitionListener(listener: SpeechRecognitionListener) {
            this.speechListener = listener
            initModel()
        }

        private fun initModel() {
            onStateChanged(SpeechRecognitionState.LOADING)
            StorageService.unpack(
                context,
                "model-small-ru",
                "model",
                {
                    model = it
                    onStateChanged(SpeechRecognitionState.READY)
                }, {
                    onStateChanged(SpeechRecognitionState.ERROR)
                }
            )
        }

        private fun onStateChanged(state: SpeechRecognitionState) {
            speechListener?.onState(state)
        }
    }
}