package com.velvet.hearyou.permission

interface CheckPermission {

    fun checkPermission(permission: String): Boolean
}