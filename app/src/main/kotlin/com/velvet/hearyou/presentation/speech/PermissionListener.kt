package com.velvet.hearyou.presentation.speech

import com.velvet.hearyou.presentation.permission.CheckPermission
import com.velvet.hearyou.presentation.permission.RequirePermission

interface PermissionListener : RequirePermission, CheckPermission