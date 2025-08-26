package com.konradjurkowski.moviehub.core.domain.model

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

data class ValidationResult(
    val successful: Boolean = false,
    val errorMessage: StringResource? = null,
)

@Composable
fun ValidationResult.toDisplay(): String {
    return this.errorMessage?.let { stringResource(it) } ?: ""
}
