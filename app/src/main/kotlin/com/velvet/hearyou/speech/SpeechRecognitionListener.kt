package com.velvet.hearyou.speech

interface SpeechRecognitionListener {

    fun onSpeechRecognized(data: String)

    fun onState(state: SpeechRecognitionState)
}