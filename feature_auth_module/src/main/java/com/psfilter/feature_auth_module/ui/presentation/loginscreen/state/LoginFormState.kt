package com.psfilter.feature_auth_module.ui.presentation.loginscreen.state

import com.psfilter.feature_auth_module.ui.AuthFields

data class LoginFormState(
    val email: AuthFields.Email = AuthFields.Email(""),
    val password: AuthFields.Password = AuthFields.Password(""),
    val emailError: String? = null,
    val passwordError: String? = null,
)