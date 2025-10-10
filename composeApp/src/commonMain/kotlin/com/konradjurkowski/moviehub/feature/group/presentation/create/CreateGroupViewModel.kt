package com.konradjurkowski.moviehub.feature.group.presentation.create

import androidx.lifecycle.viewModelScope
import com.konradjurkowski.moviehub.core.architecture.BaseViewModel
import com.konradjurkowski.moviehub.core.domain.model.ActionState
import com.konradjurkowski.moviehub.core.domain.model.ImageData
import com.konradjurkowski.moviehub.core.domain.model.Response
import com.konradjurkowski.moviehub.core.utils.coroutines.DispatchersProvider
import com.konradjurkowski.moviehub.core.utils.helpers.isGalleryPermissionGranted
import com.konradjurkowski.moviehub.core.utils.helpers.requestGalleryPermission
import com.konradjurkowski.moviehub.feature.group.domain.repository.GroupRepository
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupEvent.OpenGallery
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupEvent.ShowError
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupIntent.CreateClick
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupIntent.DescriptionChanged
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupIntent.DismissPermissionDialog
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupIntent.EditImageClick
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupIntent.ImageChanged
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupIntent.NameChanged
import com.konradjurkowski.moviehub.feature.group.presentation.create.CreateGroupIntent.OpenAppSettings
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.launch

class CreateGroupViewModel(
    val permissionsController: PermissionsController,
    private val groupRepository: GroupRepository,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<CreateGroupIntent, CreateGroupState, CreateGroupEvent>(
    initialState = CreateGroupState(),
) {

    override fun processIntent(intent: CreateGroupIntent) {
        when (intent) {
            is CreateClick -> createGroup(name = intent.name, description = intent.description, image = intent.image)
            is DescriptionChanged -> updateState { copy(description = intent.description) }
            DismissPermissionDialog -> updateState { copy(showPermissionDialog = false) }
            is EditImageClick -> onEditImageClick()
            is ImageChanged -> updateState { copy(image = intent.image) }
            is NameChanged -> updateState { copy(name = intent.name) }
            OpenAppSettings -> permissionsController.openAppSettings()
        }
    }

    private fun createGroup(name: String, description: String, image: ImageData?) {
        // TODO ADD VALIDATION

        updateState { copy(createState = ActionState.Loading) }
        viewModelScope.launch(dispatchersProvider.io) {
            val result = groupRepository.createGroup(name = name, description = description, image = image?.array)
            when (result) {
                is Response.Success -> {
                    // TODO
                    updateState { copy(createState = ActionState.Success) }
                }

                is Response.Failure -> {
                    sendEvent(ShowError(result.error))
                    updateState { copy(createState = ActionState.Failure) }
                }
            }
        }
    }

    private fun onEditImageClick() = viewModelScope.launch {
        if (permissionsController.isGalleryPermissionGranted()) {
            sendEvent(OpenGallery)
            return@launch
        }
        permissionsController.requestGalleryPermission(
            onGranted = { sendEvent(OpenGallery) },
            onDeniedAlways = { updateState { copy(showPermissionDialog = true) } },
        )
    }
}
