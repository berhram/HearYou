package com.velvet.hearyou.presentation

interface ManagePermission {

    fun setListener(listener: PermissionListener)

    fun requirePermission(permission: String): Boolean

    class Base : ManagePermission {

        private var listener: PermissionListener? = null

        override fun setListener(listener: PermissionListener) {
            this.listener = listener
        }

        override fun requirePermission(permission: String): Boolean =
            listener?.requirePermission(permission) ?: false
    }
}