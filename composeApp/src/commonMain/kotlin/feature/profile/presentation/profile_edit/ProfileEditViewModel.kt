package feature.profile.presentation.profile_edit

import cafe.adriel.voyager.core.model.screenModelScope
import core.architecture.BaseViewModel
import core.tools.dispatcher.DispatchersProvider
import core.tools.validator.FormValidator
import core.utils.GenericException
import core.utils.Resource
import feature.auth.data.remote.AuthService
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
            is ProfileEditIntent.DescriptionChanged -> updateViewState { copy(description = intent.description) }
            is ProfileEditIntent.ImageChanged -> updateViewState { copy(image = intent.image) }
            is ProfileEditIntent.NameChanged -> updateViewState { copy(name = intent.name) }
            ProfileEditIntent.OnEditImagePressed ->  sendSideEffect(ProfileEditSideEffect.OpenGalleryOrCheckPermission)
            is ProfileEditIntent.SavePressed -> sendUserData(intent.name, intent.description, intent.image?.array)
            ProfileEditIntent.DismissPermissionDialog -> updateViewState { copy(showPermissionDialog = false) }
            ProfileEditIntent.ShowPermissionDialog -> updateViewState { copy(showPermissionDialog = true) }
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

        updateViewState { copy(editState = Resource.Loading) }
        if (image != null) {
            screenModelScope.launch(dispatchersProvider.io) {
                when (val result = authService.uploadImage(image)) {
                    is Resource.Success -> {
                        val imageUrl = result.data
                        updateUserData(name, description, imageUrl)
                    }
                    is Resource.Failure -> {
                        sendSideEffect(ProfileEditSideEffect.ShowError(result.error))
                        updateViewState { copy(editState = Resource.Failure(result.error)) }
                    }
                    else -> {
                        // NO - OP
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
            sendSideEffect(ProfileEditSideEffect.ShowError(error))
            updateViewState { copy(editState = Resource.Failure(error)) }
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
                is Resource.Success -> {
                    authService.getAppUser(refresh = true)
                    sendSideEffect(ProfileEditSideEffect.ShowSuccessAndNavigateBack)
                }
                is Resource.Failure -> {
                    sendSideEffect(ProfileEditSideEffect.ShowError(result.error))
                }
                else -> {
                    // NO - OP
                }
            }
            updateViewState { copy(editState = result) }
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
