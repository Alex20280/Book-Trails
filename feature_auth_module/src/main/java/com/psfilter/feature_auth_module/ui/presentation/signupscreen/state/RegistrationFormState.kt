package com.psfilter.feature_auth_module.ui.presentation.signupscreen.state

data class RegistrationFormState(
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val emailError: String? = null,
    val nameError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null
)