package feature.permissions.di

import feature.permissions.presentation.notification.NotificationPermissionViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val permissionsModule = module {
    factoryOf(::NotificationPermissionViewModel)
}
