package com.konradjurkowski.moviehub.core.utils.tools

import com.konradjurkowski.moviehub.core.utils.PlatformInfo
import com.konradjurkowski.moviehub.core.utils.isAndroid
import com.konradjurkowski.moviehub.core.utils.isIOS
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.camera.CAMERA
import dev.icerock.moko.permissions.gallery.GALLERY
import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION

enum class PermissionResult {
    GRANTED,
    DENIED,
    DENIED_ALWAYS,
}

suspend fun PermissionsController.requestPermission(permission: Permission): PermissionResult {
    return try {
        this.providePermission(permission)
        PermissionResult.GRANTED
    } catch (_: DeniedAlwaysException) {
        PermissionResult.DENIED_ALWAYS
    } catch (_: DeniedException) {
        PermissionResult.DENIED
    }
}

suspend fun PermissionsController.requestPermission(
    permission: Permission,
    onGranted: () -> Unit = {},
    onDenied: () -> Unit = {},
    onDeniedAlways: () -> Unit = {},
) {
    when (requestPermission(permission)) {
        PermissionResult.GRANTED -> onGranted()
        PermissionResult.DENIED -> onDenied()
        PermissionResult.DENIED_ALWAYS -> onDeniedAlways()
    }
}

// Camera
suspend fun PermissionsController.requestCameraPermission(): PermissionResult {
    return requestPermission(Permission.CAMERA)
}

suspend fun PermissionsController.requestCameraPermission(
    onGranted: () -> Unit = {},
    onDenied: () -> Unit = {},
    onDeniedAlways: () -> Unit = {},
) {
    requestPermission(Permission.CAMERA, onGranted, onDenied, onDeniedAlways)
}

suspend fun PermissionsController.isCameraPermissionGranted(): Boolean {
    return isPermissionGranted(Permission.CAMERA)
}

// Gallery
suspend fun PermissionsController.requestGalleryPermission(): PermissionResult {
    return requestPermission(Permission.GALLERY)
}

suspend fun PermissionsController.requestGalleryPermission(
    onGranted: () -> Unit = {},
    onDenied: () -> Unit = {},
    onDeniedAlways: () -> Unit = {},
) {
    requestPermission(Permission.GALLERY, onGranted, onDenied, onDeniedAlways)
}

suspend fun PermissionsController.isGalleryPermissionGranted(): Boolean {
    return if (PlatformInfo.isAndroid()) true else isPermissionGranted(Permission.GALLERY)
}

// Notification
suspend fun PermissionsController.requestNotificationPermission(): PermissionResult {
    return requestPermission(Permission.REMOTE_NOTIFICATION)
}

suspend fun PermissionsController.requestNotificationPermission(
    onGranted: () -> Unit = {},
    onDenied: () -> Unit = {},
    onDeniedAlways: () -> Unit = {},
) {
    requestPermission(Permission.REMOTE_NOTIFICATION, onGranted, onDenied, onDeniedAlways)
}

suspend fun PermissionsController.isNotificationPermissionGranted(): Boolean {
    val isPermissionRequired = PlatformInfo.isIOS() || (PlatformInfo.isAndroid() && PlatformInfo.sdkInt >= 33)
    return !isPermissionRequired || isPermissionGranted(Permission.REMOTE_NOTIFICATION)
}
