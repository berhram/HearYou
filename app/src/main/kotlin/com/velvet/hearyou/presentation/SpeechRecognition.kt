package com.velvet.hearyou.presentation

interface SpeechRecognition {

    fun startRecognition()

    fun stopRecognition(): String
}