package com.velvet.hearyou.presentation

interface ManagePermission {

    fun checkPermission(permission: String): Boolean

    fun requirePermission(permission: String): Boolean
}