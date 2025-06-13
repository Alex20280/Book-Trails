package com.psfilter.feature_auth_module.ui.presentation

import android.util.Patterns
import java.util.regex.Pattern

object ValidationUtils {

    sealed class ValidationResult {
        data object Success : ValidationResult()
        data class Error(val message: String) : ValidationResult()
    }

    /**
     * Email validation according to requirements:
     * - Standard format (user@example.com)
     * - Length from 6 to 320 characters
     * - Special characters: _, ., -
     * - A valid top-level domain
     * - No spaces or extra special characters
     * - Registry-independent
     */
    fun validateEmail(email: String): ValidationResult {
        val trimmedEmail = email.trim().lowercase()

        if (trimmedEmail.isEmpty()) {
            return ValidationResult.Error("Email cannot be empty")
        }

        if (trimmedEmail.length < 6) {
            return ValidationResult.Error("Email must contain a minimum of 6 characters")
        }

        if (trimmedEmail.length > 320) {
            return ValidationResult.Error("Email cannot contain more than 320 characters")
        }

        if (trimmedEmail.contains(" ")) {
            return ValidationResult.Error("Email must not contain spaces")
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            return ValidationResult.Error("Incorrect email format")
        }

        val allowedEmailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        if (!Pattern.matches(allowedEmailPattern, trimmedEmail)) {
            return ValidationResult.Error("Email contains invalid characters")
        }

        val domainPart = trimmedEmail.substringAfter("@")
        if (!domainPart.contains(".") || domainPart.endsWith(".")) {
            return ValidationResult.Error("Email must contain a valid domain")
        }

        val tld = domainPart.substringAfterLast(".")
        if (tld.length < 2) {
            return ValidationResult.Error("Invalid top-level domain")
        }

        return ValidationResult.Success
    }

    /**
     * Password validation according to the requirements:
     * - Length from 8 to 20 characters
     * - Case sensitive
     * - Minimum 1 capital letter
     * - Minimum 1 digit
     * - Minimum 1 special character
     */
    private fun validatePassword(password: String): ValidationResult {
        if (password.isEmpty()) {
            return ValidationResult.Error("Password cannot be empty")
        }

        if (password.length < 8) {
            return ValidationResult.Error("Password must contain a minimum of 8 characters")
        }

        if (password.length > 20) {
            return ValidationResult.Error("Password cannot contain more than 20 characters")
        }

        if (!password.any { it.isUpperCase() }) {
            return ValidationResult.Error("Password must contain at least 1 capital letter")
        }

        if (!password.any { it.isDigit() }) {
            return ValidationResult.Error("Password must contain at least 1 digit")
        }

        val specialChars = " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~"

        if (!password.any { specialChars.contains(it) }) {
            return ValidationResult.Error("Password must contain at least 1 special character")
        }

        val allowedChars = ('a'..'z') + ('A'..'Z') + ('0'..'9') + specialChars.toList()
        if (!password.all { it in allowedChars }) {
            return ValidationResult.Error("Password contains invalid characters")
        }

        return ValidationResult.Success
    }


    private fun validateName(name: String): ValidationResult {
        val trimmedName = name.trim()

        if (trimmedName.isEmpty()) {
            return ValidationResult.Error("Name can't be empty")
        }

        if (trimmedName.length < 2) {
            return ValidationResult.Error("Name must contain a minimum of 2 character")
        }

        if (trimmedName.length > 50) {
            return ValidationResult.Error("Name cannot contain more than 50 characters")
        }

        val namePattern = "^[a-zA-Zа-яёА-ЯЁ\\s'-]+$"
        if (!Pattern.matches(namePattern, trimmedName)) {
            return ValidationResult.Error("Name can only contain letters, spaces, hyphens, and apostrophes")
        }

        return ValidationResult.Success
    }

    private fun validateCode(code: String): ValidationResult {
        val trimmedCode = code.trim()

        if (trimmedCode.isEmpty()) {
            return ValidationResult.Error("Code cannot be empty")
        }

        if (trimmedCode.length < 4) {
            return ValidationResult.Error("Code must be exactly 4 characters long")
        }

        if (trimmedCode.length > 4) {
            return ValidationResult.Error("Code must be exactly 4 characters long")
        }

        if (trimmedCode.contains(" ")) {
            return ValidationResult.Error("Code must not contain spaces")
        }

        if (!trimmedCode.all { it.isLetterOrDigit() }) {
            return ValidationResult.Error("Code must contain only letters and digits")
        }

        return ValidationResult.Success
    }

    private fun validatePasswordConfirmation(password: String, confirmPassword: String): ValidationResult {
        if (confirmPassword.isEmpty()) {
            return ValidationResult.Error("Confirm password")
        }

        if (password != confirmPassword) {
            return ValidationResult.Error("Passwords don't match")
        }

        return ValidationResult.Success
    }


    fun validateEmailForm(email: String): String? {
        return when (val validation = validateEmail(email)) {
            is ValidationResult.Error -> validation.message
            is ValidationResult.Success -> null
        }
    }

    fun validateCodeForm(email: String): String? {
        return when (val validation = validateCode(email)) {
            is ValidationResult.Error -> validation.message
            is ValidationResult.Success -> null
        }
    }

    fun validateNameForm(name: String): String? {
        return when (val validation = validateName(name)) {
            is ValidationResult.Error -> validation.message
            is ValidationResult.Success -> null
        }
    }

    fun validatePasswordForm(password: String): String? {
        return when (val validation = validatePassword(password)) {
            is ValidationResult.Error -> validation.message
            is ValidationResult.Success -> null
        }
    }

    fun validatePasswordConfirmationForm(password: String, confirmPassword: String): String? {
        return when (val validation = validatePasswordConfirmation(password, confirmPassword)) {
            is ValidationResult.Error -> validation.message
            is ValidationResult.Success -> null
        }
    }

}