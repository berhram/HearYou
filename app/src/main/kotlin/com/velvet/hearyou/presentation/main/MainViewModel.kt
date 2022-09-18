package com.velvet.hearyou.presentation.main

import android.Manifest
import androidx.lifecycle.ViewModel
import com.velvet.hearyou.presentation.ManagePermission
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class MainViewModel(
    private val managePermission: ManagePermission
) : ViewModel(), ContainerHost<MainState, MainEffect> {

    override val container: Container<MainState, MainEffect> =
        container(MainState(onGrantClick = { grantPermission() }, onStartStopClick = {}, isPermissionGranted = managePermission.checkPermission(Manifest.permission.RECORD_AUDIO)))

    private fun grantPermission() = managePermission.requirePermission(Manifest.permission.RECORD_AUDIO)
}