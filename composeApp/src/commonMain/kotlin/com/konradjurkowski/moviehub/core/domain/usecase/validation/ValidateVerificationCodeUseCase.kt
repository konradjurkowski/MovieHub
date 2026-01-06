package com.konradjurkowski.moviehub.core.domain.usecase.validation

import com.konradjurkowski.moviehub.core.domain.model.validation.ValidationResult
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.empty_field
import moviehub.composeapp.generated.resources.invalid_code

class ValidateVerificationCodeUseCase {

    private companion object Companion {
        val format = Regex("^\\d{6}$")
    }

    operator fun invoke(text: String): ValidationResult {
        if (text.isBlank()) {
            return ValidationResult(errorMessage = Res.string.empty_field)
        }

        if (!text.matches(format)) {
            return ValidationResult(errorMessage = Res.string.invalid_code)
        }

        return ValidationResult(successful = true)
    }
}
