package com.psfilter.feature_auth_module.ui.presentation.forgetpasswordscreen.state

import com.psfilter.feature_auth_module.ui.AuthFields

data class ForgetPasswordFormState(
    val email: AuthFields.Email = AuthFields.Email(""),
    val emailError: String? = null,
    val code: AuthFields.VerificationCode = AuthFields.VerificationCode(""),
    val codeError: String? = null,
)