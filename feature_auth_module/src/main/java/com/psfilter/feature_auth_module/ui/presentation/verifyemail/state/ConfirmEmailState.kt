package com.psfilter.feature_auth_module.ui.presentation.verifyemail.state

data class ConfirmEmailState(
    val email: String = "",
    val code: String = "",
    val emailError: String? = null,
)