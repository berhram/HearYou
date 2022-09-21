package com.velvet.hearyou.presentation.permission

interface CheckPermission {

    fun checkPermission(permission: String): Boolean
}