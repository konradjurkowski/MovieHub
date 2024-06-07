package core.tools.validator

interface FormValidator {
    fun basicValidation(text: String): ValidationResult
    fun validateEmail(email: String): ValidationResult
    fun validatePassword(password: String): ValidationResult
    fun validateRepeatedPassword(password: String, repeatedPassword: String): ValidationResult
}
