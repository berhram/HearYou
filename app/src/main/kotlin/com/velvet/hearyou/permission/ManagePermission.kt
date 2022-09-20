package com.velvet.hearyou.permission

import com.velvet.hearyou.speech.PermissionListener

interface ManagePermission : CheckPermission, RequirePermission {

    fun setListener(listener: PermissionListener)

    class Base : ManagePermission {

        private var listener: PermissionListener? = null

        override fun setListener(listener: PermissionListener) {
            this.listener = listener
        }

        override fun requirePermission(permission: String) {
            listener?.requirePermission(permission)
        }

        override fun checkPermission(permission: String): Boolean =
            listener?.checkPermission(permission) ?: false
    }
}