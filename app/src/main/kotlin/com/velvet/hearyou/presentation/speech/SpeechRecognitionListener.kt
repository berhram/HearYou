package com.velvet.hearyou.presentation.speech

interface SpeechRecognitionListener {

    fun onSpeechRecognized(data: String)

    fun onState(state: SpeechRecognitionState)
}