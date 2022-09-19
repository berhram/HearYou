package com.velvet.hearyou.presentation

interface PermissionListener {

    fun requirePermission(permission: String): Boolean
}