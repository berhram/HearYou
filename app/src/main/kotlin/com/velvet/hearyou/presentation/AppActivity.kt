package com.velvet.hearyou.presentation

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.velvet.hearyou.presentation.main.MainScreen
import com.velvet.hearyou.presentation.main.MainViewModel
import com.velvet.hearyou.presentation.permission.ManagePermission
import com.velvet.hearyou.presentation.speech.PermissionListener
import com.velvet.hearyou.presentation.ui.AppTheme
import com.velvet.hearyou.presentation.ui.SystemUISetup
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

class AppActivity : ComponentActivity(), PermissionListener {

    private val permissionCache: ManagePermission by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContent {
            AppTheme {
                SystemUISetup()
                val state = koinViewModel<MainViewModel>().collectAsState().value
                MainScreen(state)
            }
        }
        permissionCache.setListener(this)
    }

    override fun checkPermission(permission: String): Boolean =
        checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

    override fun requirePermission(permission: String) =
        requestPermissions(arrayOf(permission), 0)
}