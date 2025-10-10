package com.konradjurkowski.moviehub.core.domain.usecase.validation

import com.konradjurkowski.moviehub.core.domain.model.validation.ValidationResult
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.empty_field
import moviehub.composeapp.generated.resources.invalid_email

class ValidateEmailUseCase {

    private companion object {
        val format = Regex(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
    }

    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(errorMessage = Res.string.empty_field)
        }

        if (!email.matches(format)) {
            return ValidationResult(errorMessage = Res.string.invalid_email)
        }

        return ValidationResult(successful = true)
    }
}
