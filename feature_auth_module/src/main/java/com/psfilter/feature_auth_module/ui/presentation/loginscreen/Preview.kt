package com.psfilter.feature_auth_module.ui.presentation.loginscreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.feature_auth_module.ui.LoginScreenUI
import com.psfilter.feature_auth_module.ui.AuthFields
import com.psfilter.feature_auth_module.ui.presentation.loginscreen.state.LoginFormState


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    // Create a mock form state for preview
    val mockFormState = LoginFormState(
        email = AuthFields.Email("example@email.com"),
        password = AuthFields.Password(""),
        emailError = null,
        passwordError = null
    )

    LoginScreenUI(
        paddingValues = PaddingValues(0.dp),
        showLoader = false,
        formState = mockFormState,
        onEmailChange = {},
        onPasswordChange = {},
        onClickForgetPassword = {},
        onEmailFocusLost = {},
        onPasswordFocusLost = {},
        onVerifyEmail = {},
        onRegisterClick = {},
        onGoogleSignInCLick = {},
        onSubmitButtonClick = {},
        showVerificationDialog = false,
        onOverlayVisibilityChange = {},
        isButtonEnabled = true
    )
}

// Preview with loading state
@Preview(showBackground = true)
@Composable
fun LoginScreenLoadingPreview() {
    val mockFormState = LoginFormState(
        email = AuthFields.Email(""),
        password = AuthFields.Password(""),
        emailError = null,
        passwordError = null
    )

    LoginScreenUI(
        paddingValues = PaddingValues(0.dp),
        showLoader = true,
        formState = mockFormState,
        onEmailChange = {},
        onPasswordChange = {},
        onClickForgetPassword = {},
        onEmailFocusLost = {},
        onPasswordFocusLost = {},
        onVerifyEmail = {},
        onRegisterClick = {},
        onGoogleSignInCLick = {},
        onSubmitButtonClick = {},
        showVerificationDialog = false,
        onOverlayVisibilityChange = {},
        isButtonEnabled = false
    )
}

// Preview with error state
@Preview(showBackground = true)
@Composable
fun LoginScreenErrorPreview() {
    val mockFormState = LoginFormState(
        email = AuthFields.Email("invalid-email"),
        password = AuthFields.Password("123"),
        emailError = "Please enter a valid email address",
        passwordError = "Password must be at least 8 characters"
    )

    LoginScreenUI(
        paddingValues = PaddingValues(0.dp),
        showLoader = false,
        formState = mockFormState,
        onEmailChange = {},
        onPasswordChange = {},
        onClickForgetPassword = {},
        onEmailFocusLost = {},
        onPasswordFocusLost = {},
        onVerifyEmail = {},
        onRegisterClick = {},
        onGoogleSignInCLick = {},
        onSubmitButtonClick = {},
        showVerificationDialog = false,
        onOverlayVisibilityChange = {},
        isButtonEnabled = false
    )
}