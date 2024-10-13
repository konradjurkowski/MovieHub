package feature.profile.presentation.profile_edit

import core.architecture.MviIntent
import core.architecture.MviSideEffect
import core.architecture.MviState
import core.utils.Resource
import feature.auth.domain.AppUser
import org.jetbrains.compose.resources.StringResource

@MviIntent
sealed class ProfileEditIntent {
    data object DismissPermissionDialog : ProfileEditIntent()
    data object ShowPermissionDialog : ProfileEditIntent()
    data object OnEditImagePressed : ProfileEditIntent()
    data class DescriptionChanged(val description: String) : ProfileEditIntent()
    data class ImageChanged(val image: ByteArrayWrapper) : ProfileEditIntent()
    data class NameChanged(val name: String) : ProfileEditIntent()
    data class SavePressed(val name: String, val description: String, val image: ByteArrayWrapper?) : ProfileEditIntent()
}

@MviSideEffect
sealed class ProfileEditSideEffect {
    data object ShowSuccessAndNavigateBack : ProfileEditSideEffect()
    data class ShowError(val error: Throwable) : ProfileEditSideEffect()
    data object OpenGalleryOrCheckPermission : ProfileEditSideEffect()
}

@MviState
data class ProfileEditState(
    val appUser: AppUser? = null,
    val image: ByteArrayWrapper? = null,
    val name: String = "",
    val nameError: StringResource? = null,
    val description: String = "",
    val descriptionError: StringResource? = null,
    val showPermissionDialog: Boolean = false,
    val editState: Resource<Any> = Resource.Idle,
)

class ByteArrayWrapper(val array: ByteArray) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ByteArrayWrapper) return false
        return array.contentEquals(other.array)
    }

    override fun hashCode(): Int {
        return array.contentHashCode()
    }
}
