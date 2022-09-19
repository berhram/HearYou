package com.velvet.hearyou.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.velvet.hearyou.presentation.ManagePermission
import com.velvet.hearyou.presentation.SpeechRecognition
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import org.orbitmvi.orbit.compose.collectAsState

class MainNode(
    buildContext: BuildContext,
    private val managePermission: ManagePermission,
    private val speechRecognition: SpeechRecognition
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        val state =
            getViewModel<MainViewModel>(parameters = { parametersOf(managePermission, speechRecognition) }).collectAsState().value
        MainScreen(state)
    }
}