package com.psfilter.feature_auth_module.domain.validationservice

class EmailState : TextFieldState(
    validator = { email -> isEmailValid(email) },
    errorMessage = { email -> emailErrorMessage(email) }
)

private fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

private fun emailErrorMessage(email: String) = "Email $email is invalid"

