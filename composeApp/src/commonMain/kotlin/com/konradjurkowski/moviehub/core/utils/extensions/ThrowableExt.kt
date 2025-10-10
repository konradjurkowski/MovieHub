package com.konradjurkowski.moviehub.core.utils.extensions

import com.konradjurkowski.moviehub.core.utils.exceptions.CustomException
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.something_went_wrong

fun Throwable.getErrorMessage() = when (this) {
    is CustomException -> this.messageRes
    else -> Res.string.something_went_wrong
}
