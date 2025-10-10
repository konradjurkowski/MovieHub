package com.konradjurkowski.moviehub.core.domain.usecase.validation

import com.konradjurkowski.moviehub.core.domain.model.validation.ValidationResult
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.empty_field
import moviehub.composeapp.generated.resources.passwords_do_not_match

class ValidatePasswordMatchUseCase {

    operator fun invoke(password: String, confirmPassword: String): ValidationResult {
        if (confirmPassword.isBlank()) {
            return ValidationResult(errorMessage = Res.string.empty_field)
        }

        if (password != confirmPassword) {
            return ValidationResult(errorMessage = Res.string.passwords_do_not_match)
        }

        return ValidationResult(successful = true)
    }
}
