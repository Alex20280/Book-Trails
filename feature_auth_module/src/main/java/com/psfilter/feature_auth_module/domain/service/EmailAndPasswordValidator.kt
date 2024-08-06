package com.psfilter.feature_auth_module.domain.service

import com.booktrails.core_module.errorhandling.DataError
import com.booktrails.core_module.errorhandling.RequestResult

class EmailAndPasswordValidator() {

    fun isEmailValid(email: String): RequestResult<Boolean, DataError> {
        return if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            RequestResult.Success(true)
        } else {
            RequestResult.Error(DataError.AuthenticationError.LOGIN_IS_NOT_CORRECT)
        }
    }


    fun isPasswordValid(password: String): RequestResult<Boolean, DataError> {
        // Password should be at least 8 characters long
        if (password.length < 8) {
            return RequestResult.Error(DataError.AuthenticationError.PASSWORD_IS_TOO_SHORT)
        }
        // Check for at least one uppercase letter
        val uppercasePattern = "[A-Z]".toRegex()
        if (!uppercasePattern.containsMatchIn(password)) {
            return RequestResult.Error(DataError.AuthenticationError.PASSWORD_NOT_HAVE_UPPER_CASE_LETTER)
        }

        // Check for at least one digit
        val digitPattern = "\\d".toRegex()
        if (!digitPattern.containsMatchIn(password)) {
            return RequestResult.Error(DataError.AuthenticationError.PASSWORD_NOT_HAVE_DIGITS)
        }

        // Check for at least one special character
        val specialCharacterPattern = "[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]".toRegex()
        if (!specialCharacterPattern.containsMatchIn(password)) {
            return RequestResult.Error(DataError.AuthenticationError.PASSWORD_NOT_SPECIAL_CHARACTERS)
        }
        return RequestResult.Success(true)
    }

    fun validateLoginEmailAndPassword(email: String, password: String): RequestResult<Boolean, DataError> {
        // Email validation
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return RequestResult.Error(DataError.AuthenticationError.LOGIN_IS_NOT_CORRECT)
        }

        // Password validation
        // Password should be at least 8 characters long
        if (password.length < 8) {
            return RequestResult.Error(DataError.AuthenticationError.PASSWORD_IS_TOO_SHORT)
        }

        // Check for at least one uppercase letter
        if (!password.contains(Regex("[A-Z]"))) {
            return RequestResult.Error(DataError.AuthenticationError.PASSWORD_NOT_HAVE_UPPER_CASE_LETTER)
        }

        // Check for at least one digit
        if (!password.contains(Regex("\\d"))) {
            return RequestResult.Error(DataError.AuthenticationError.PASSWORD_NOT_HAVE_DIGITS)
        }

        // Check for at least one special character
        if (!password.contains(Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]"))) {
            return RequestResult.Error(DataError.AuthenticationError.PASSWORD_NOT_SPECIAL_CHARACTERS)
        }

        // If all validations pass
        return RequestResult.Success(true)
    }

    fun validateRegistration(email: String, password: String, confirmPassword: String): RequestResult<Boolean, DataError> {

        if (password != confirmPassword) {
            return RequestResult.Error(DataError.AuthenticationError.PASSWORDS_NOT_MATCH)
        }
        // Email validation
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return RequestResult.Error(DataError.AuthenticationError.LOGIN_IS_NOT_CORRECT)
        }

        // Password validation
        // Password should be at least 8 characters long
        if (password.length < 8) {
            return RequestResult.Error(DataError.AuthenticationError.PASSWORD_IS_TOO_SHORT)
        }

        // Check for at least one uppercase letter
        if (!password.contains(Regex("[A-Z]"))) {
            return RequestResult.Error(DataError.AuthenticationError.PASSWORD_NOT_HAVE_UPPER_CASE_LETTER)
        }

        // Check for at least one digit
        if (!password.contains(Regex("\\d"))) {
            return RequestResult.Error(DataError.AuthenticationError.PASSWORD_NOT_HAVE_DIGITS)
        }

        // Check for at least one special character
        if (!password.contains(Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]"))) {
            return RequestResult.Error(DataError.AuthenticationError.PASSWORD_NOT_SPECIAL_CHARACTERS)
        }

        // If all validations pass
        return RequestResult.Success(true)
    }
}