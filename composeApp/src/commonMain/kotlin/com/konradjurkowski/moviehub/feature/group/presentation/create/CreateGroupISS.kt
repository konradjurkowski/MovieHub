package com.konradjurkowski.moviehub.feature.group.presentation.create

import com.konradjurkowski.moviehub.core.architecture.MviIntent
import com.konradjurkowski.moviehub.core.architecture.MviEvent
import com.konradjurkowski.moviehub.core.architecture.MviState
import com.konradjurkowski.moviehub.core.domain.model.ActionState
import com.konradjurkowski.moviehub.core.domain.model.ImageData
import com.konradjurkowski.moviehub.core.domain.model.validation.ValidationResult

@MviIntent
sealed class CreateGroupIntent {
    data class NameChanged(val name: String) : CreateGroupIntent()
    data class DescriptionChanged(val description: String) : CreateGroupIntent()
    data object EditImageClick : CreateGroupIntent()
    data class ImageChanged(val image: ImageData) : CreateGroupIntent()
    data object OpenAppSettings: CreateGroupIntent()
    data object DismissPermissionDialog : CreateGroupIntent()
    data class CreateClick(
        val name: String,
        val description: String,
        val image: ImageData?,
    ) : CreateGroupIntent()
}

@MviEvent
sealed class CreateGroupEvent {
    data class ShowError(val error: Throwable) : CreateGroupEvent()
    data object OpenGallery : CreateGroupEvent()
}

@MviState
data class CreateGroupState(
    val name: String = "",
    val nameValidation: ValidationResult = ValidationResult(successful = true),
    val description: String = "",
    val image: ImageData? = null,
    val showPermissionDialog: Boolean = false,
    val createState: ActionState = ActionState.Idle,
)
