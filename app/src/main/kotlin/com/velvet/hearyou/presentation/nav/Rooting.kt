package com.velvet.hearyou.presentation.nav

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Routing : Parcelable {

    @Parcelize
    object Main : Routing()
}
