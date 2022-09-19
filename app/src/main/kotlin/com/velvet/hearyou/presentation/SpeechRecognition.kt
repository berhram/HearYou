package com.velvet.hearyou.presentation

import android.content.Context

import org.vosk.Model
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.vosk.android.StorageService
import java.lang.Exception

interface SpeechRecognition {

    fun startRecognition()

    fun stopRecognition()

    fun setSpeechRecognitionListener(listener: SpeechRecognitionListener)

    class Base(private val context: Context) : SpeechRecognition, RecognitionListener {


        private lateinit var listener: SpeechRecognitionListener

        private lateinit var model: Model

        private fun initModel() {
            StorageService.unpack(
                context,
                "vosk-model-small-ru-0.22",
                "model",
                {
                    model = it
                }, {
                    throw it
                }
            )
        }

        override fun onPartialResult(hypothesis: String) {
            listener.onSpeechRecognized(hypothesis)

        }

        override fun onResult(hypothesis: String) {
            listener.onSpeechRecognized(hypothesis)
        }

        override fun onFinalResult(hypothesis: String) {
        }

        override fun onError(exception: Exception) {
        }

        override fun onTimeout() {
        }

        override fun startRecognition() {

        }

        override fun stopRecognition() {
            TODO("Not yet implemented")
        }

        override fun setSpeechRecognitionListener(listener: SpeechRecognitionListener) {
            this.listener = listener
        }
    }
}