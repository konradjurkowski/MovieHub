package com.konradjurkowski.moviehub.core.data.api.dto

import kotlinx.serialization.Serializable
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.something_went_wrong
import org.jetbrains.compose.resources.StringResource

@Serializable
data class ApiError(
    val message: String? = null,
    val code: ApiErrorType,
)

enum class ApiErrorType {
    // Authentication
    INVALID_CREDENTIALS,
    EMAIL_ALREADY_EXISTS,
    INVALID_REFRESH_TOKEN,
    USER_NOT_AUTHENTICATED,

    // User errors
    USER_NOT_FOUND,

    // Movies error
    MOVIE_ALREADY_EXISTS,

    // Other
    GENERIC_ERROR;

    val labelRes: StringResource get() = when (this) {
        INVALID_CREDENTIALS -> Res.string.something_went_wrong
        EMAIL_ALREADY_EXISTS -> Res.string.something_went_wrong
        INVALID_REFRESH_TOKEN -> Res.string.something_went_wrong
        USER_NOT_AUTHENTICATED -> Res.string.something_went_wrong
        USER_NOT_FOUND -> Res.string.something_went_wrong
        MOVIE_ALREADY_EXISTS -> Res.string.something_went_wrong
        GENERIC_ERROR -> Res.string.something_went_wrong
    }
}
