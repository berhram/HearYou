package com.velvet.hearyou.presentation.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackSlider
import com.velvet.hearyou.presentation.ManagePermission
import com.velvet.hearyou.presentation.main.MainNode

class RootNode(
    buildContext: BuildContext,
    private val managePermission: ManagePermission,
    private val backStack: BackStack<Routing> = BackStack(
        Routing.Main,
        buildContext.savedStateMap
    )
) : ParentNode<Routing>(
    backStack,
    buildContext
) {

    override fun resolve(routing: Routing, buildContext: BuildContext): Node = when (routing) {
        is Routing.Main -> MainNode(buildContext, managePermission)
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(
            navModel = backStack,
            transitionHandler = rememberBackstackSlider()
        )
    }
}