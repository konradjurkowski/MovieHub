package com.konradjurkowski.moviehub.feature.auth.presentation.activate

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.domain.events.EventBus
import com.konradjurkowski.moviehub.core.domain.model.ActionState
import com.konradjurkowski.moviehub.core.domain.model.QrCodeScanned
import com.konradjurkowski.moviehub.core.domain.model.Response
import com.konradjurkowski.moviehub.core.domain.usecase.validation.ValidateVerificationCodeUseCase
import com.konradjurkowski.moviehub.core.navigation.AppNavigator
import com.konradjurkowski.moviehub.core.navigation.CoreDestination.MainRoute
import com.konradjurkowski.moviehub.core.navigation.CoreDestination.QrCodeScannerRoute
import com.konradjurkowski.moviehub.core.utils.coroutines.DispatchersProvider
import com.konradjurkowski.moviehub.core.utils.extensions.removeWhitespace
import com.konradjurkowski.moviehub.core.utils.helpers.isCameraPermissionGranted
import com.konradjurkowski.moviehub.core.utils.helpers.requestCameraPermission
import com.konradjurkowski.moviehub.feature.auth.domain.repository.AuthRepository
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination.ActivateAccountRoute
import com.konradjurkowski.moviehub.feature.auth.navigation.AuthDestination.NotificationPermissionRoute
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.ise.ActivateAccountEvent
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.ise.ActivateAccountEvent.ShowError
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.ise.ActivateAccountIntent
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.ise.ActivateAccountIntent.ActivateAccountPressed
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.ise.ActivateAccountIntent.ActivationCodeChanged
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.ise.ActivateAccountIntent.DismissPermissionDialog
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.ise.ActivateAccountIntent.OpenAppSettings
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.ise.ActivateAccountIntent.ResendCodePressed
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.ise.ActivateAccountIntent.ScanQrCodePressed
import com.konradjurkowski.moviehub.feature.auth.presentation.activate.ise.ActivateAccountState
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

class ActivateAccountViewModel(
    val permissionsController: PermissionsController,
    private val authRepository: AuthRepository,
    private val eventBus: EventBus,
    private val navigator: AppNavigator,
    private val validateVerificationCode: ValidateVerificationCodeUseCase,
    private val dispatchersProvider: DispatchersProvider,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ActivateAccountIntent, ActivateAccountState, ActivateAccountEvent>(
    initialState = ActivateAccountState(),
) {

    private var args = savedStateHandle.toRoute<ActivateAccountRoute>()

    init {
        initializeListeners()
    }

    override fun processIntent(intent: ActivateAccountIntent) = when (intent) {
        is ActivateAccountPressed -> onActivateAccountClick(intent.code)
        is ActivationCodeChanged -> updateState { copy(code = intent.code) }
        DismissPermissionDialog -> updateState { copy(showPermissionDialog = false) }
        OpenAppSettings -> permissionsController.openAppSettings()
        ResendCodePressed -> onResendCodeClick()
        ScanQrCodePressed -> onScanQrCodeClick()
    }

    private fun onScanQrCodeClick() {
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

    private fun onResendCodeClick() {
        if (state.resendState.isLoading()) return

        updateState { copy(resendState = ActionState.Loading) }
        viewModelScope.launch(dispatchersProvider.io) {
            when (val result = authRepository.sendActivationCode(args.email)) {
                is Response.Success -> {
                    // TODO SHOW SUCCESS
                    updateState { copy(resendState = ActionState.Success) }
                }

                is Response.Failure -> {
                    sendEvent(ShowError(result.error))
                    updateState { copy(resendState = ActionState.Failure) }
                }
            }
        }
    }

    private fun onActivateAccountClick(code: String) {
        if (state.activationState.isLoading()) return

        val codeValidation = validateVerificationCode(code)
        updateState { copy(codeValidation = codeValidation) }
        if (!codeValidation.successful) return

        updateState { copy(activationState = ActionState.Loading) }
        viewModelScope.launch(dispatchersProvider.io) {
            when (val result = authRepository.activateAccount(email = args.email, code = code.removeWhitespace())) {
                is Response.Success -> {
                    navigateForward()
                    updateState { copy(activationState = ActionState.Success) }
                }

                is Response.Failure -> {
                    sendEvent(ShowError(result.error))
                    updateState { copy(activationState = ActionState.Failure) }
                }
            }
        }
    }

    private suspend fun navigateForward() {
        if (authRepository.isInitialLaunch()) {
            navigator.replaceAll(NotificationPermissionRoute)
            return
        }

        navigator.replaceAll(MainRoute)
        return
    }

    private fun initializeListeners() {
        viewModelScope.launch(dispatchersProvider.default) {
            eventBus.events
                .filterIsInstance<QrCodeScanned>()
                .collectLatest { updateState { copy(code = it.qrCode) } }
        }
    }
}
