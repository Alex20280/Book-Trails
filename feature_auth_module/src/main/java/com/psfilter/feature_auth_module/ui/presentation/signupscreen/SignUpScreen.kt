package com.project.feature_auth_module.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.booktrails.ui_module.CustomPasswordTextField
import com.booktrails.ui_module.CustomInputTextField
import com.booktrails.ui_module.R
import com.booktrails.ui_module.SubmitButton
import com.booktrails.ui_module.VerifyEmailDialog
import com.network_module.errorhandling.DataError
import com.psfilter.feature_auth_module.ui.presentation.signupscreen.state.RegistrationFormState
import com.psfilter.feature_auth_module.ui.presentation.signupscreen.state.RegistrationUiState
import com.psfilter.feature_auth_module.ui.presentation.signupscreen.viewmodel.SignUpViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    paddingValues: PaddingValues,
    navigateToVerifyEmailScreen: () -> Unit,
    onTosClick: () -> Unit,
    onPrivacyClick: () -> Unit,
) {

    val context = LocalContext.current

    val showLoader = remember { mutableStateOf(false) }

    val viewModel: SignUpViewModel = koinViewModel()

    val registrationState by viewModel.registrationState.collectAsState()
    val formState by viewModel.formState

    var showVerificationDialog by remember { mutableStateOf(false) }

    LaunchedEffect(registrationState) {
        when (val currentState = registrationState) {
            is RegistrationUiState.Success -> {
                showLoader.value = false
                navigateToVerifyEmailScreen.invoke()
            }

            is RegistrationUiState.Loading -> {
                showLoader.value = true
            }

            is RegistrationUiState.Error -> {
                showLoader.value = false
                if (currentState.error == DataError.EmailPasswordAuth.ACCOUNT_ALREADY_EXISTS_BUT_NOT_VERIFIED) {
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

    SignUpScreenUI(
        paddingValues = paddingValues,
        showLoader = showLoader.value,
        formState = formState,
        onEmailChange = viewModel::updateEmail,
        onNameChange = viewModel::updateName,
        onPasswordChange = viewModel::updatePassword,
        onConfirmPasswordChange = viewModel::updateConfirmPassword,
        onRegisterButtonClick = { viewModel.registerNewUser() },
        onTosClick = onTosClick,
        onPrivacyClick = onPrivacyClick,
        onEmailFocusLost = viewModel::validateEmailOnFocusLost,
        onNameFocusLost = viewModel::validateNameOnFocusLost,
        onPasswordFocusLost = viewModel::validatePasswordOnFocusLost,
        onConfirmPasswordFocusLost = viewModel::validateConfirmPasswordOnFocusLost,
        isButtonEnabled = viewModel.isRegistrationButtonEnabled(),
        onVerifyEmail = navigateToVerifyEmailScreen,
        showVerificationDialog = showVerificationDialog,
        onOverlayVisibilityChange = { showVerificationDialog = it }
    )
}

@Composable
fun SignUpScreenUI(
    paddingValues: PaddingValues,
    formState: RegistrationFormState,
    onEmailChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    showLoader: Boolean,
    onRegisterButtonClick: () -> Unit,
    onTosClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    onEmailFocusLost: () -> Unit,
    onNameFocusLost: () -> Unit,
    onPasswordFocusLost: () -> Unit,
    onConfirmPasswordFocusLost: () -> Unit,
    isButtonEnabled: Boolean,
    onVerifyEmail: () -> Unit,
    showVerificationDialog: Boolean,
    onOverlayVisibilityChange: (Boolean) -> Unit

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
            painter = painterResource(id = R.drawable.signup_ic),
            contentDescription = null,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = stringResource(R.string.sign_up_title),
            style = MaterialTheme.typography.headlineLarge,
            fontFamily = FontFamily(Font(R.font.roboto_bold)),
            fontSize = 26.sp,
            color = colorResource(id = R.color.dark_brown),
            modifier = Modifier.padding(top = 15.dp, start = 12.dp),
        )

        Spacer(modifier = Modifier.height(12.dp))

        CustomInputTextField(
            value = formState.email,
            onValueChange = onEmailChange,
            onFocusChanged = { hasFocus ->
                if (!hasFocus) onEmailFocusLost()
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = stringResource(R.string.email),
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

        Spacer(modifier = Modifier.height(10.dp))

        CustomInputTextField(
            value = formState.name,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth(),
            onFocusChanged = { hasFocus ->
                if (!hasFocus) onNameFocusLost()
            },
            placeholder = stringResource(R.string.name),
            isError = formState.nameError != null
        )
        formState.nameError?.let { error ->
            Text(
                text = error,
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.red),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, start = 4.dp)
            )
        }


        Spacer(modifier = Modifier.height(10.dp))

        CustomPasswordTextField(
            value = formState.password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            onFocusChanged = { hasFocus ->
                if (!hasFocus) onPasswordFocusLost()
            },
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

        Spacer(modifier = Modifier.height(10.dp))

        CustomPasswordTextField(
            value = formState.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            onFocusChanged = { hasFocus ->
                if (!hasFocus) onConfirmPasswordFocusLost()
            },
            placeholder = stringResource(R.string.confirm_password),
            isError = formState.confirmPasswordError != null
        )
        formState.confirmPasswordError?.let { error ->
            Text(
                text = error,
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.red),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, start = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        SubmitButton(
            stringResource(com.project.feature_auth_module.R.string.create),
            isButtonEnabled,
            {
                onRegisterButtonClick.invoke()
            },
        )

        Spacer(modifier = Modifier.height(2.dp))

        val annotatedString = buildAnnotatedString {
            append(stringResource(com.project.feature_auth_module.R.string.by_signing_up_you_agree_to_book_trails))
            val robotoRegular = FontFamily(Font(R.font.roboto_regular))

            pushStringAnnotation(
                tag = stringResource(R.string.tos),
                annotation = stringResource(R.string.termofservice)
            )
            withStyle(
                style = SpanStyle(
                    color = colorResource(id = R.color.antique_rose),
                    fontFamily = robotoRegular,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(stringResource(R.string.term_of_service))
            }
            pop()

            append(stringResource(R.string.and))

            pushStringAnnotation(
                tag = stringResource(R.string.privacy), annotation = stringResource(
                    R.string.privacypolicy
                )
            )
            withStyle(
                style = SpanStyle(
                    color = colorResource(id = R.color.antique_rose),
                    fontFamily = robotoRegular,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(stringResource(R.string.privacy_policy))
            }
            pop()
        }

        ClickableText(
            text = annotatedString,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "TOS", start = offset, end = offset)
                    .firstOrNull()?.let {
                        onTosClick.invoke()
                    }
                annotatedString.getStringAnnotations(tag = "PRIVACY", start = offset, end = offset)
                    .firstOrNull()?.let {
                        onPrivacyClick.invoke()
                    }
            }
        )


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
                onVerifyEmail,
                onOverlayVisibilityChange = onOverlayVisibilityChange
            )
        }
    }
}