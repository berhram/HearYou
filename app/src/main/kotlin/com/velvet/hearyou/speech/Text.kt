package com.velvet.hearyou.speech

import com.google.gson.annotations.SerializedName

data class Text(
    @SerializedName("text")
    val text: String
)
