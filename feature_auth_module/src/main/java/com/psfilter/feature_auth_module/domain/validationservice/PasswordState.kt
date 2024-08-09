package com.psfilter.feature_auth_module.domain.validationservice

class PasswordState : TextFieldState(
    validator = { password -> isPasswordValid(password).first },
    errorMessage = { password -> isPasswordValid(password).second }
)

fun isPasswordValid(password: String): Pair<Boolean, String> {
    if (password.length < 8) {
        return Pair(false, "Password should be at least 8 characters long")
    }

    val uppercasePattern = "[A-Z]".toRegex()
    if (!uppercasePattern.containsMatchIn(password)) {
        return Pair(false, "Password should have at least 1 uppercase letter")
    }

    val digitPattern = "\\d".toRegex()
    if (!digitPattern.containsMatchIn(password)) {
        return Pair(false, "Password should have at least 1 digit")
    }

    val specialCharacterPattern = "[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]".toRegex()
    if (!specialCharacterPattern.containsMatchIn(password)) {
        return Pair(false, "Password should have at least 1 special character")
    }

    return Pair(true, "")
}