package com.psfilter.feature_auth_module.domain.validationservice

class ConfirmPasswordState(
    private val originalPassword: () -> String
) : TextFieldState(
    validator = { confirmPassword -> isPasswordSame(confirmPassword, originalPassword()) },
    errorMessage = { email -> emailErrorMessage(email) }
)

private fun isPasswordSame(confirmPassword: String, originalPassword: String): Boolean {
    return confirmPassword == originalPassword
}

private fun emailErrorMessage(emil: String) = "Password not match"

