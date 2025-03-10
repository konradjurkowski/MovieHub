package feature.profile.presentation.profile_edit

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.model.ActionState
import core.model.Response
import core.tools.dispatcher.DispatchersProvider
import core.tools.validator.FormValidator
import core.utils.GenericException
import feature.auth.data.remote.AuthService
import feature.profile.presentation.profile_edit.ProfileEditIntent.DescriptionChanged
import feature.profile.presentation.profile_edit.ProfileEditIntent.DismissPermissionDialog
import feature.profile.presentation.profile_edit.ProfileEditIntent.ImageChanged
import feature.profile.presentation.profile_edit.ProfileEditIntent.NameChanged
import feature.profile.presentation.profile_edit.ProfileEditIntent.OnEditImagePressed
import feature.profile.presentation.profile_edit.ProfileEditIntent.SavePressed
import feature.profile.presentation.profile_edit.ProfileEditIntent.ShowPermissionDialog
import feature.profile.presentation.profile_edit.ProfileEditSideEffect.OpenGalleryOrCheckPermission
import feature.profile.presentation.profile_edit.ProfileEditSideEffect.ShowError
import feature.profile.presentation.profile_edit.ProfileEditSideEffect.ShowSuccessAndNavigateBack
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileEditViewModel(
    private val authService: AuthService,
    private val formValidator: FormValidator,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<ProfileEditIntent, ProfileEditSideEffect, ProfileEditState>() {

    init {
        initializeListeners()
    }

    override fun getDefaultState() = ProfileEditState()

    override fun processIntent(intent: ProfileEditIntent) {
        when (intent) {
            is DescriptionChanged -> updateViewState { copy(description = intent.description) }
            is ImageChanged -> updateViewState { copy(image = intent.image) }
            is NameChanged -> updateViewState { copy(name = intent.name) }
            OnEditImagePressed ->  sendSideEffect(OpenGalleryOrCheckPermission)
            is SavePressed -> sendUserData(intent.name, intent.description, intent.image?.array)
            DismissPermissionDialog -> updateViewState { copy(showPermissionDialog = false) }
            ShowPermissionDialog -> updateViewState { copy(showPermissionDialog = true) }
        }
    }

    private fun sendUserData(
        name: String,
        description: String,
        image: ByteArray?,
    ) {
        if (viewState.value.editState.isLoading()) return

        val nameValidation = formValidator.basicValidation(name)
        updateViewState { copy(nameError = nameValidation.errorMessage) }
        if (!nameValidation.successful) return

        updateViewState { copy(editState = ActionState.Loading) }
        if (image != null) {
            screenModelScope.launch(dispatchersProvider.io) {
                when (val result = authService.uploadImage(image)) {
                    is Response.Success -> {
                        val imageUrl = result.data
                        updateUserData(name, description, imageUrl)
                    }
                    is Response.Failure -> {
                        updateViewState { copy(editState = ActionState.Failure(result.error)) }
                        sendSideEffect(ShowError(result.error))
                    }
                }
            }
            return
        }

        updateUserData(name, description)
    }

    private fun updateUserData(
        name: String,
        description: String,
        imageUrl: String? = null,
    ) {
        val user = viewState.value.appUser
        if (user == null) {
            val error = GenericException()
            sendSideEffect(ShowError(error))
            updateViewState { copy(editState = ActionState.Failure(error)) }
            return
        }

        val updatedUser = user.copy(
            name = name.trim(),
            description = description.trim(),
            imageUrl = imageUrl ?: user.imageUrl,
        )
        screenModelScope.launch(dispatchersProvider.io) {
            val result = authService.updateAppUser(updatedUser)
            when (result) {
                is Response.Success -> {
                    authService.getAppUser(refresh = true)
                    updateViewState { copy(editState = ActionState.Success) }
                    sendSideEffect(ShowSuccessAndNavigateBack)
                }
                is Response.Failure -> {
                    updateViewState { copy(editState = ActionState.Failure(result.error)) }
                    sendSideEffect(ShowError(result.error))
                }
            }
        }
    }

    private fun initializeListeners() {
        screenModelScope.launch {
            authService.appUser.collectLatest { appUser ->
                updateViewState {
                    copy(
                        appUser = appUser,
                        name = appUser?.name ?: "",
                        description = appUser?.description ?: "",
                    )
                }
            }
        }
    }
}
