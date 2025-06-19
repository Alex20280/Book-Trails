package com.project.feature_auth_module.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.booktrails.ui_module.CustomPasswordTextField
import com.booktrails.ui_module.CustomInputTextField
import com.booktrails.ui_module.R
import com.booktrails.ui_module.SubmitButton
import com.booktrails.ui_module.VerifyEmailDialog
import com.network_module.errorhandling.DataError
import com.psfilter.feature_auth_module.ui.presentation.loginscreen.viewmodel.LoginViewModel
import com.psfilter.feature_auth_module.ui.presentation.loginscreen.state.LoginUiState
import com.psfilter.feature_auth_module.ui.presentation.loginscreen.state.LoginFormState
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    paddingValues: PaddingValues,
    onClickForgetPassword: () -> Unit,
    onVerifyEmailClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onSignInClick: (String) -> Unit,
) {

    val context = LocalContext.current
    val viewModel: LoginViewModel = koinViewModel()
    val showLoader = remember { mutableStateOf(false) }
    val loginState by viewModel.loginState.collectAsState()
    var showVerificationDialog by remember { mutableStateOf(false) }
    val formState by viewModel.formState

    LaunchedEffect(loginState) {
        when (val currentState = loginState) {
            is LoginUiState.Success -> {
                showLoader.value = false
                val accessToken = currentState.response.accessToken
                onSignInClick(accessToken)
            }

            is LoginUiState.Loading -> {
                showLoader.value = true
            }

            is LoginUiState.Error -> {
                showLoader.value = false
                if (currentState.error == DataError.EmailPasswordAuth.EMAIL_NOT_VERIFIED) {
                    showVerificationDialog = true
                } else {
                    Toast.makeText(context, currentState.message, Toast.LENGTH_LONG).show()
                }
            }

            else -> {
                showLoader.value = false
            }
        }
    }

    LoginScreenUI(
        paddingValues = paddingValues,
        showLoader = showLoader.value,
        formState = formState,
        onEmailChange = viewModel::updateEmail,
        onPasswordChange = viewModel::updatePassword,
        onClickForgetPassword = onClickForgetPassword,
        onEmailFocusLost = viewModel::validateEmailOnFocusLost,
        onPasswordFocusLost = viewModel::validatePasswordOnFocusLost,
        onVerifyEmail = onVerifyEmailClick,
        onRegisterClick = onRegisterClick,
        onGoogleSignInCLick = {}, //TODO
        onSubmitButtonClick = { viewModel.loginUser() },
        showVerificationDialog = showVerificationDialog,
        onOverlayVisibilityChange = { showVerificationDialog = it },
        isButtonEnabled = viewModel.isLoginButtonEnabled(),
    )
}

@Composable
fun LoginScreenUI(
    paddingValues: PaddingValues,
    showLoader: Boolean,
    formState: LoginFormState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onClickForgetPassword: () -> Unit,
    onEmailFocusLost: () -> Unit,
    onPasswordFocusLost: () -> Unit,
    onVerifyEmail: () -> Unit,
    onRegisterClick: () -> Unit,
    onGoogleSignInCLick: () -> Unit,
    onSubmitButtonClick: () -> Unit,
    showVerificationDialog: Boolean,
    onOverlayVisibilityChange: (Boolean) -> Unit,
    isButtonEnabled: Boolean,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.floral_white))
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.back_ic),
            contentDescription = null,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = stringResource(R.string.sign_in_title),
            style = MaterialTheme.typography.headlineLarge,
            fontFamily = FontFamily(Font(R.font.roboto_bold)),
            fontSize = 26.sp,
            color = colorResource(id = R.color.dark_brown),
            modifier = Modifier.padding(top = 15.dp, start = 16.dp),
        )

        Spacer(modifier = Modifier.height(12.dp))

        CustomInputTextField(
            value = formState.email.raw,
            onValueChange = { value ->
                onEmailChange(value.trimEnd())
            },
            onFocusChanged = { hasFocus ->
                if (!hasFocus) onEmailFocusLost()
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = stringResource(R.string.login),
            isError = formState.emailError != null
        )
        formState.emailError?.let { error ->
            Text(
                text = error,
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.red),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, start = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        CustomPasswordTextField(
            value = formState.password.raw,
            onFocusChanged = { hasFocus ->
                if (!hasFocus) onPasswordFocusLost()
            },
            onValueChange = { value ->
                onPasswordChange(value.trimEnd())
            },                                        //{ passwordText.value = AuthFields.Password(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = stringResource(R.string.password),
            isError = formState.passwordError != null
        )
        formState.passwordError?.let { error ->
            Text(
                text = error,
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.red),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, start = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            Text(
                modifier = Modifier
                    .clickable {
                        if (!showLoader) onClickForgetPassword.invoke()
                    }
                    .padding(top = 8.dp, end = 8.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.antique_rose),
                text = stringResource(R.string.forget_password),
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        SubmitButton(stringResource(com.project.feature_auth_module.R.string.submit), isButtonEnabled, { onSubmitButtonClick.invoke() })

        Spacer(modifier = Modifier.height(2.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontSize = 16.sp,
                color = colorResource(id = R.color.antique_rose),
                style = MaterialTheme.typography.bodyMedium,
                text = stringResource(R.string.don_t_have_an_account)
            )

            TextButton(onClick = { if (!showLoader) onRegisterClick.invoke() }) {
                Text(
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.antique_rose),
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(R.string.register)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = FontFamily(Font(R.font.roboto_medium)),
            fontSize = 16.sp,
            color = colorResource(id = R.color.antique_rose),
            text = stringResource(R.string.or)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                modifier = Modifier.padding(end = 6.dp),
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                fontSize = 16.sp,
                color = colorResource(id = R.color.antique_rose),
                text = stringResource(R.string.continue_with_google)
            )

            Image(
                painter = painterResource(id = R.drawable.google_icon),
                contentDescription = stringResource(R.string.google_icon),
                modifier = Modifier
                    .size(54.dp)
                    .clickable {
                        if (!showLoader) onGoogleSignInCLick.invoke()
                    }
            )

            Spacer(modifier = Modifier.width(16.dp))

        }
    }
    if (showVerificationDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .padding(start = 16.dp, end = 16.dp)
                .clickable { onOverlayVisibilityChange(false) },
            contentAlignment = Alignment.Center
        ) {
            VerifyEmailDialog(
                text = stringResource(com.project.feature_auth_module.R.string.please_verify_your_email_address),
                onVerifyEmail,
                onOverlayVisibilityChange = onOverlayVisibilityChange
            )
        }
    }

    if (showLoader) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.Black.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = colorResource(id = R.color.antique_rose)
            )
        }
    }
}