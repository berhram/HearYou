package com.velvet.hearyou.speech

import androidx.annotation.StringRes
import com.velvet.hearyou.R

enum class SpeechRecognitionState(@StringRes val message: Int) {
    NONE(R.string.no_data_available),
    LOADING(R.string.loading),
    READY(R.string.ready),
    ERROR(R.string.error)
}