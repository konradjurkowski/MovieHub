package com.konradjurkowski.moviehub.core.utils.exceptions

import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.something_went_wrong
import org.jetbrains.compose.resources.StringResource

abstract class CustomException(
    val messageRes: StringResource = Res.string.something_went_wrong,
) : Throwable()

class ApiException(errorRes: StringResource) : CustomException(errorRes)
