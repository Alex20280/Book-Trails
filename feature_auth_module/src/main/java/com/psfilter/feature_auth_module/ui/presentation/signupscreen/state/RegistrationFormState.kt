package com.psfilter.feature_auth_module.ui.presentation.signupscreen.state

import com.psfilter.feature_auth_module.ui.AuthFields

data class RegistrationFormState(
    val email: AuthFields.Email = AuthFields.Email(""),
    val name: AuthFields.Name = AuthFields.Name(""),
    val password: AuthFields.Password = AuthFields.Password(""),
    val confirmPassword: AuthFields.ConfirmPassword = AuthFields.ConfirmPassword(""),
    val emailError: String? = null,
    val nameError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null
)