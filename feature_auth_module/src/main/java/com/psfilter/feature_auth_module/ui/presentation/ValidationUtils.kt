package com.psfilter.feature_auth_module.ui.presentation

import android.util.Patterns
import java.util.regex.Pattern

object ValidationUtils {

    sealed class ValidationResult {
        object Success : ValidationResult()
        data class Error(val message: String) : ValidationResult()
    }

    /**
     * Валидация email согласно требованиям:
     * - Стандартный формат (user@example.com)
     * - Длина от 6 до 320 символов
     * - Специальные символы: _, ., -
     * - Действующий домен верхнего уровня
     * - Без пробелов и лишних спецсимволов
     * - Регистронезависимый
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
     * Валидация пароля согласно требованиям:
     * - Длина от 8 до 20 символов
     * - Чувствителен к регистру
     * - Минимум 1 заглавная буква
     * - Минимум 1 цифра
     * - Минимум 1 специальный символ
     */
    fun validatePassword(password: String): ValidationResult {
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


    fun validateName(name: String): ValidationResult {
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

    fun validatePasswordConfirmation(password: String, confirmPassword: String): ValidationResult {
        if (confirmPassword.isEmpty()) {
            return ValidationResult.Error("Confirm password")
        }

        if (password != confirmPassword) {
            return ValidationResult.Error("Passwords don't match")
        }

        return ValidationResult.Success
    }

    /**
     * Комплексная валидация всех полей регистрации
     */
    fun validateRegistrationData(
        email: String,
        password: String,
        name: String,
        confirmPassword: String
    ): Map<String, ValidationResult> {
        return mapOf(
            "email" to validateEmail(email),
            "password" to validatePassword(password),
            "name" to validateName(name),
            "confirmPassword" to validatePasswordConfirmation(password, confirmPassword)
        )
    }

    /**
     * Проверка, все ли валидации прошли успешно
     */
    fun areAllValidationsSuccessful(validations: Map<String, ValidationResult>): Boolean {
        return validations.values.all { it is ValidationResult.Success }
    }

    /**
     * Получение всех ошибок валидации
     */
    fun getValidationErrors(validations: Map<String, ValidationResult>): Map<String, String> {
        return validations.mapNotNull { (key, result) ->
            when (result) {
                is ValidationResult.Error -> key to result.message
                is ValidationResult.Success -> null
            }
        }.toMap()
    }

/*    private val emailRegex = Regex(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$.?"
    )

    private val specialChars = setOf(
        ' ', '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/',
        ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'
    )

    fun validateEmail(email: String): ValidationResult {
        return if (email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ValidationResult.Success
        } else {
            ValidationResult.Error("Неверный формат email")
        }
    }

    fun validatePassword(password: String): ValidationResult {
        return if (password.length >= 6) {
            ValidationResult.Success
        } else {
            ValidationResult.Error("Пароль должен содержать минимум 6 символов")
        }
    }

    fun validateName(name: String): ValidationResult {
        return if (name.isNotEmpty()) {
            ValidationResult.Success
        } else {
            ValidationResult.Error("Имя не может быть пустым")
        }
    }

    fun validatePasswordConfirmation(password: String, confirmPassword: String): ValidationResult {
        return if (password == confirmPassword) {
            ValidationResult.Success
        } else {
            ValidationResult.Error("Пароли не совпадают")
        }
    }*/


}