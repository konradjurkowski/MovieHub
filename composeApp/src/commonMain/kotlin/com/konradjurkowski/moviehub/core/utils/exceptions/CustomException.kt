package com.konradjurkowski.moviehub.core.utils.exceptions

import com.konradjurkowski.moviehub.core.data.api.dto.ApiErrorType
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.something_went_wrong
import org.jetbrains.compose.resources.StringResource

abstract class CustomException(
    val messageRes: StringResource = Res.string.something_went_wrong,
) : Throwable()

class ApiException(
    override val message: String? = null,
    val code: ApiErrorType,
) : CustomException(code.labelRes)
