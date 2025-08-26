package com.konradjurkowski.moviehub.feature.auth.presentation.notification

import androidx.compose.runtime.Composable
import com.konradjurkowski.moviehub.feature.auth.presentation.notification.comp.NotificationPermissionContent
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parameterSetOf

@Composable
fun NotificationPermissionScreen() {
    val factory = rememberPermissionsControllerFactory()
    val viewModel = koinViewModel<NotificationPermissionViewModel> { parameterSetOf(factory.createPermissionsController()) }

    BindEffect(viewModel.permissionsController)

    NotificationPermissionContent(onIntent = viewModel::sendIntent)

}
