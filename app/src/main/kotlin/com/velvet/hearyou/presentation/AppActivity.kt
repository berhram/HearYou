package com.velvet.hearyou.presentation

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.setContent
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeComponentActivity
import com.velvet.hearyou.presentation.nav.RootNode
import com.velvet.hearyou.presentation.ui.AppTheme
import com.velvet.hearyou.presentation.ui.SystemUISetup

class AppActivity : NodeComponentActivity(), ManagePermission {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContent {
            AppTheme {
                SystemUISetup()
                NodeHost(integrationPoint = integrationPoint) {
                    RootNode(buildContext = it, managePermission = this)
                }
            }
        }
    }

    override fun checkPermission(permission: String): Boolean = checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

    override fun requirePermission(permission: String): Boolean {
        requestPermissions(arrayOf(permission), 0)
        return checkPermission(permission)
    }
}