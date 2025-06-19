package com.project.feature_auth_module.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.booktrails.ui_module.CustomCodeTextField
import com.booktrails.ui_module.CustomInputTextField
import com.booktrails.ui_module.R
import com.booktrails.ui_module.SubmitButton
import com.network_module.errorhandling.DataError
import com.psfilter.feature_auth_module.ui.presentation.forgetpasswordscreen.state.ForgetPasswordFormState
import com.psfilter.feature_auth_module.ui.presentation.forgetpasswordscreen.state.ForgetPasswordUiState
import com.psfilter.feature_auth_module.ui.presentation.forgetpasswordscreen.viewmodel.ForgetPasswordViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForgetPasswordScreen(
    paddingValues: PaddingValues,
    navigateToCreateNewPassword: (String, String) -> Unit,
    onCreateAccountClick: () -> Unit,
) {

    val context = LocalContext.current
    val viewModel: ForgetPasswordViewModel = koinViewModel()
    val showLoader = remember { mutableStateOf(false) }
    val isVerificationCodeSentOut = remember { mutableStateOf(false) }
    val requestVerificationCodeState by viewModel.requestVerificationCodeState.collectAsState()
    val isFirstRequest by viewModel.isFirstRequest.collectAsState()
    val isInitialCodeRequest by viewModel.isInitialCodeRequest.collectAsState()
    var showAccountNotFoundDialog by remember { mutableStateOf(false) }
    val formState by viewModel.formState
    val showCodeField by viewModel.showCodeField.collectAsState()

    var resendTimer by remember { mutableStateOf(0) }
    var isResendEnabled by remember { mutableStateOf(true) }



    LaunchedEffect(resendTimer) {
        if (resendTimer > 0) {
            kotlinx.coroutines.delay(1000L)
            resendTimer--
        } else {
            isResendEnabled = true
        }
    }

    LaunchedEffect(requestVerificationCodeState) {
        when (val currentState = requestVerificationCodeState) {
            is ForgetPasswordUiState.Success -> {
                showLoader.value = false
                viewModel.setShowCodeField(true)
                isVerificationCodeSentOut.value = true

                kotlinx.coroutines.delay(5000L)
                isVerificationCodeSentOut.value = false
            }

            is ForgetPasswordUiState.Loading -> {
                showLoader.value = true
            }

            is ForgetPasswordUiState.Error -> {
                showLoader.value = false
                if (currentState.error == DataError.ResendEmailVerificationCodeAuth.NOT_FOUND) {
                    showAccountNotFoundDialog = true
                } else {
                    Toast.makeText(context, currentState.message, Toast.LENGTH_LONG).show()
                }
            }

            else -> {
                viewModel.setShowCodeField(false)
                showLoader.value = false
            }
        }
    }

    ForgetPasswordScreenUI(
        viewModel = viewModel,
        showLoader = showLoader.value,
        paddingValues = paddingValues,
        onSendCodeAgainClick = {
            if (isResendEnabled) {
                val success = viewModel.sendForgetPasswordCode(isInitialCodeRequest)
                if (success) {
                    resendTimer = 30
                    isResendEnabled = false
                }
            }
        },
        onRestoreClick = {
            val success = viewModel.sendForgetPasswordCode(isFirstRequest)
            if (success) {
                resendTimer = 30
                isResendEnabled = false
            }
        },
        onCreateAccountClick = onCreateAccountClick,
        isButtonEnabled = viewModel.isLoginButtonEnabled(),
        onEmailChange = viewModel::updateEmail,
        onEmailFocusLost = viewModel::validateEmailOnFocusLost,
        onCodeChange = viewModel::updateCode,
        onCodeFocusLost = viewModel::validateCodeOnFocusLost,
        onOverlayVisibilityChange = { showAccountNotFoundDialog = it },
        showAccountNotFoundDialog = showAccountNotFoundDialog,
        formState = formState,
        isVerificationCodeSentOut = isVerificationCodeSentOut.value,
        showCodeField = showCodeField,
        forgetPasswordState = requestVerificationCodeState,
        navigateToCreateNewPassword = navigateToCreateNewPassword,
        resendTimer = resendTimer,
        isResendEnabled = isResendEnabled,

    )
}

@Composable
fun ForgetPasswordScreenUI(
    showLoader: Boolean,
    paddingValues: PaddingValues,
    onSendCodeAgainClick: () -> Unit,
    onRestoreClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    isButtonEnabled: Boolean,
    isVerificationCodeSentOut: Boolean,
    onEmailChange: (String) -> Unit,
    onEmailFocusLost: () -> Unit,
    onCodeFocusLost: () -> Unit,
    onCodeChange: (String) -> Unit,
    onOverlayVisibilityChange: (Boolean) -> Unit,
    showAccountNotFoundDialog: Boolean,
    formState: ForgetPasswordFormState,
    forgetPasswordState: ForgetPasswordUiState,
    showCodeField: Boolean,
    navigateToCreateNewPassword: (String, String) -> Unit,
    viewModel: ForgetPasswordViewModel,
    resendTimer: Int,
    isResendEnabled: Boolean,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.floral_white))
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            ),
        contentAlignment = Alignment.Center

    ) {

        Column(
            modifier = Modifier.padding(start = 15.dp, end = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.lock_large_ic),
                contentDescription = null,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Text(
                text = stringResource(R.string.reset_password),
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                fontSize = 26.sp,
                color = colorResource(id = R.color.dark_brown),
                modifier = Modifier.padding(top = 10.dp),
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 0.dp),
                placeholder = stringResource(R.string.login),
                isError = formState.emailError != null,
                borderTint = isVerificationCodeSentOut
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 10.dp, end = 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                if (isVerificationCodeSentOut) {
                    Text(
                        text = stringResource(com.project.feature_auth_module.R.string.request_was_sent_out),
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.salad_green),
                        modifier = Modifier.weight(1f)
                    )
                }

                Text(
                    text = if (isResendEnabled || resendTimer == 0) {
                        stringResource(com.project.feature_auth_module.R.string.send_code_again)
                    } else {
                        "Повторить через $resendTimer сек"
                    },
                    style = MaterialTheme.typography.bodySmall.copy(
                        textDecoration = if (isResendEnabled) TextDecoration.Underline else TextDecoration.None,
                        fontWeight = FontWeight.Bold
                    ),
                    color = if (isResendEnabled) {
                        colorResource(id = R.color.antique_rose)
                    } else {
                        colorResource(id = R.color.dark_brown).copy(alpha = 0.6f)
                    },
                    modifier = Modifier
                        .clickable(enabled = isResendEnabled) {
                            onSendCodeAgainClick.invoke()
                        }
                        .padding(0.dp),
                    textAlign = TextAlign.End
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (showCodeField){
                CustomCodeTextField(
                    isError = formState.codeError != null,
                    value = formState.code.raw,
                    onValueChange = onCodeChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = stringResource(com.project.feature_auth_module.R.string.code),
                    onFocusChanged = { hasFocus ->
                        if (!hasFocus) onCodeFocusLost()
                    }
                )
                formState.codeError?.let { error ->
                    Text(
                        text = error,
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(id = R.color.red),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp, start = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            SubmitButton(
                stringResource(com.project.feature_auth_module.R.string.restore),
                isButtonEnabled,
                onClick = {
                    if (forgetPasswordState is ForgetPasswordUiState.Success){
                        val codeToPass = formState.code.raw
                        val email = formState.email.raw
                        viewModel.resetForgetPasswordState()
                        navigateToCreateNewPassword.invoke(codeToPass, email)
                    } else {
                        onRestoreClick.invoke()
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

        if (showAccountNotFoundDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.85f))
                    .blur(radius = 32.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                    .padding(start = 16.dp, end = 16.dp)
                    .clickable { onOverlayVisibilityChange(false) },
                contentAlignment = Alignment.Center
            ) {
                AccountNotFoundDialog(
                    onCreateAccountClick = {
                        onCreateAccountClick.invoke()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onOverlayVisibilityChange = onOverlayVisibilityChange
                )
            }
        }
    }
}

@Composable
fun AccountNotFoundDialog(
    onCreateAccountClick: () -> Unit,
    modifier: Modifier = Modifier,
    onOverlayVisibilityChange: (Boolean) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.antique_rose),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(com.project.feature_auth_module.R.string.account_not_found_click_below_to_create_a_new_one),
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = {
                    onOverlayVisibilityChange(false)
                    onCreateAccountClick.invoke()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                border = BorderStroke(1.dp, Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = stringResource(com.project.feature_auth_module.R.string.create_account))
            }
        }
    }
}
