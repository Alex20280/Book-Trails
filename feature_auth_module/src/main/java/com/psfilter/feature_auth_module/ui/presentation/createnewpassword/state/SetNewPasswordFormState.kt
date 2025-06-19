package com.psfilter.feature_auth_module.ui.presentation.createnewpassword.state

import com.psfilter.feature_auth_module.ui.AuthFields

data class SetNewPasswordFormState(
    val email: AuthFields.Email = AuthFields.Email(""),
    val verificationCode: AuthFields.VerificationCode = AuthFields.VerificationCode(""),
    val password: AuthFields.Password = AuthFields.Password(""),
    val confirmPassword: AuthFields.ConfirmPassword = AuthFields.ConfirmPassword(""),
    val emailError: String? = null,
    val verificationCodeError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null
)