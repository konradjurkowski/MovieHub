package core.tools.validator

import org.jetbrains.compose.resources.StringResource

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: StringResource? = null
)
