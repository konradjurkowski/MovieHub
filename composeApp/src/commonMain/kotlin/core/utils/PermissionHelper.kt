package core.utils

import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController

enum class PermissionResult {
    GRANTED,
    DENIED,
    DENIED_ALWAYS,
}

suspend fun PermissionsController.requestPermission(permission: Permission): PermissionResult {
    return try {
        this.providePermission(permission)
        PermissionResult.GRANTED
    } catch (e: DeniedException) {
        PermissionResult.DENIED
    } catch (e: DeniedAlwaysException) {
        PermissionResult.DENIED_ALWAYS
    }
}
