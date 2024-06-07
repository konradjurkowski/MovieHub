package core.tools.validator

import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource

@OptIn(ExperimentalResourceApi::class)
data class ValidationResult(
    val successful: Boolean,
    val errorMessage: StringResource? = null
)
