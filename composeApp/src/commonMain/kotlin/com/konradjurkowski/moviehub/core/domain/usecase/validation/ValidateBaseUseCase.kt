package com.konradjurkowski.moviehub.core.domain.usecase.validation

import com.konradjurkowski.moviehub.core.domain.model.ValidationResult
import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.empty_field

class ValidateBaseUseCase {

    operator fun invoke(text: String): ValidationResult {
        if (text.isBlank()) {
            return ValidationResult(errorMessage = Res.string.empty_field)
        }

        return ValidationResult(successful = true)
    }
}
