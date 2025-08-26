package com.konradjurkowski.moviehub.core.domain.usecase.validation

import com.konradjurkowski.moviehub.core.domain.model.ValidationResult
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.empty_field
import moviehub.composeapp.generated.resources.invalid_password

class ValidatePasswordUseCase {

    private companion object {
        val format = Regex("^(?=.*[@#$%^&+=.])(?=\\S+$).{8,}$")
    }

    operator fun invoke(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(errorMessage = Res.string.empty_field)
        }

        if (!password.matches(format)) {
            return ValidationResult(errorMessage = Res.string.invalid_password)
        }

        return ValidationResult(successful = true)
    }
}
