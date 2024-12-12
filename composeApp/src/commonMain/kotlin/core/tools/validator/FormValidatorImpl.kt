package core.tools.validator

import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.empty_field
import moviehub.composeapp.generated.resources.invalid_email
import moviehub.composeapp.generated.resources.invalid_password
import moviehub.composeapp.generated.resources.passwords_do_not_match

class FormValidatorImpl : FormValidator {
    override fun basicValidation(text: String): ValidationResult {
        if (text.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = Res.string.empty_field,
            )
        }
        return ValidationResult(successful = true)
    }

    override fun validateEmail(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = Res.string.empty_field,
            )
        }
        if (!email.matches(ValidatorConstants.emailAddressRegex)) {
            return ValidationResult(
                successful = false,
                errorMessage = Res.string.invalid_email,
            )
        }
        return ValidationResult(successful = true)
    }

    override fun validatePassword(password: String): ValidationResult {
        val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.])(?=\\S+$).{8,}$")
        if (password.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = Res.string.empty_field,
            )
        }
        if (!passwordRegex.matches(password)) {
            return ValidationResult(
                successful = false,
                errorMessage = Res.string.invalid_password,
            )
        }
        return ValidationResult(successful = true)
    }

    override fun validateRepeatedPassword(
        password: String,
        repeatedPassword: String
    ): ValidationResult {
        if (repeatedPassword.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = Res.string.empty_field,
            )
        }
        if (password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = Res.string.passwords_do_not_match,
            )
        }
        return ValidationResult(successful = true)
    }
}
