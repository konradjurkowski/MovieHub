package com.konradjurkowski.moviehub.feature.group.presentation.join

import androidx.lifecycle.viewModelScope
import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.data.application.event.EventBus
import com.konradjurkowski.moviehub.core.data.application.event.QrCodeScanned
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.core.navigation.CoreDestination.QrCodeScannerRoute
import com.konradjurkowski.moviehub.core.utils.coroutines.DispatchersProvider
import com.konradjurkowski.moviehub.core.utils.helpers.isCameraPermissionGranted
import com.konradjurkowski.moviehub.core.utils.helpers.requestCameraPermission
import com.konradjurkowski.moviehub.feature.group.navigation.GroupDestination.CreateGroupRoute
import com.konradjurkowski.moviehub.feature.group.presentation.join.JoinGroupIntent.CreateGroupPressed
import com.konradjurkowski.moviehub.feature.group.presentation.join.JoinGroupIntent.DismissPermissionDialog
import com.konradjurkowski.moviehub.feature.group.presentation.join.JoinGroupIntent.OpenAppSettings
import com.konradjurkowski.moviehub.feature.group.presentation.join.JoinGroupIntent.InvitationCodeChanged
import com.konradjurkowski.moviehub.feature.group.presentation.join.JoinGroupIntent.JoinGroupPressed
import com.konradjurkowski.moviehub.feature.group.presentation.join.JoinGroupIntent.ScanQrCodePressed
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

class JoinGroupViewModel(
    val permissionsController: PermissionsController,
    private val navigator: AppNavigator,
    private val eventBus: EventBus,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<JoinGroupIntent, JoinGroupState, JoinGroupEvent> (
    initialState = JoinGroupState(),
) {

    init {
        initListener()
    }

    override fun processIntent(intent: JoinGroupIntent) {
        when (intent) {
            CreateGroupPressed -> navigator.push(CreateGroupRoute)
            DismissPermissionDialog -> updateState { copy(showPermissionDialog = false) }
            OpenAppSettings -> permissionsController.openAppSettings()
            is InvitationCodeChanged -> updateState { copy(invitationCode = intent.code) }
            is JoinGroupPressed -> {}
            ScanQrCodePressed -> onScanQrCodePressed()
        }
    }

    private fun onScanQrCodePressed() {
        viewModelScope.launch {
            if (permissionsController.isCameraPermissionGranted()) {
                navigator.push(QrCodeScannerRoute)
                return@launch
            }

            permissionsController.requestCameraPermission(
                onGranted = { navigator.push(QrCodeScannerRoute) },
                onDeniedAlways = { updateState { copy(showPermissionDialog = true) } },
            )
        }
    }

    private fun initListener() {
        viewModelScope.launch(dispatchersProvider.default) {
            eventBus.events
                .filterIsInstance<QrCodeScanned>()
                .collectLatest { updateState { copy(invitationCode = it.qrCode) } }
        }
    }
}
