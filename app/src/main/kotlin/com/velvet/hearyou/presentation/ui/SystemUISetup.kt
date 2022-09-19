package com.velvet.hearyou.presentation.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SystemUISetup() {
    val systemUiController = rememberSystemUiController()
    val isLightTheme = isSystemInDarkTheme()
    val systemBarColor = MaterialTheme.colors.surface
    val transparentColor: (Color) -> Color = { original ->
        systemBarColor.compositeOver(original)
    }
    SideEffect {
        systemUiController.setStatusBarColor(
            color = systemBarColor, darkIcons = isLightTheme,
            transformColorForLightContent = transparentColor
        )
        systemUiController.setNavigationBarColor(
            color = systemBarColor,
            darkIcons = isLightTheme,
            navigationBarContrastEnforced = false,
            transformColorForLightContent = transparentColor
        )
    }
}