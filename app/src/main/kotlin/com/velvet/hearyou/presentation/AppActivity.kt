package com.velvet.hearyou.presentation

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.setContent
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeComponentActivity
import com.velvet.hearyou.presentation.nav.RootNode
import com.velvet.hearyou.presentation.ui.AppTheme
import com.velvet.hearyou.presentation.ui.SystemUISetup
import org.koin.android.ext.android.inject


class AppActivity : NodeComponentActivity(), PermissionListener {

    private val permissionCache: ManagePermission by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContent {
            AppTheme {
                SystemUISetup()
                NodeHost(integrationPoint = integrationPoint) {
                    RootNode(buildContext = it)
                }
            }
        }
        permissionCache.setListener(this)
    }

    override fun requirePermission(permission: String): Boolean {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(arrayOf(permission), 0)
        return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
}