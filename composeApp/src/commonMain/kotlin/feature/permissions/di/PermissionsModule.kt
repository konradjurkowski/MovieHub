package feature.permissions.di

import feature.permissions.presentation.notification.NotificationPermissionViewModel
import org.koin.dsl.module

val permissionsModule = module {
    factory<NotificationPermissionViewModel> { NotificationPermissionViewModel() }
}
