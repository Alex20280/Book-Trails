package com.psfilter.feature_auth_module.ui.presentation.verifyemail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.booktrails.ui_module.CustomCodeTextField
import com.booktrails.ui_module.CustomInputTextField
import com.booktrails.ui_module.R
import com.booktrails.ui_module.SubmitButton
import com.psfilter.feature_auth_module.ui.presentation.verifyemail.state.ConfirmEmailState
import com.psfilter.feature_auth_module.ui.presentation.verifyemail.state.ResendDVerificationEmailState
import com.psfilter.feature_auth_module.ui.presentation.verifyemail.state.VerificationEmailUiState
import com.psfilter.feature_auth_module.ui.presentation.verifyemail.viewmodel.VerifyEmailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun VerifyEmailScreen(
    paddingValues: PaddingValues,
    navigateTLoginScreen: () -> Unit
) {

    val context = LocalContext.current
    var showLoader = remember { mutableStateOf(false) }
    val showCodeMessage = remember { mutableStateOf(false) }
    val viewModel: VerifyEmailViewModel = koinViewModel()
    val formState by viewModel.formState
    val emailVerificationState by viewModel.emailVerificationUiState.collectAsState()
    val resendEmailVerificationUiState by viewModel.resendEmailVerificationUiState.collectAsState()

    LaunchedEffect(emailVerificationState) {
        when (val currentState = emailVerificationState) {
            is VerificationEmailUiState.Success -> {
                showLoader.value = false
                navigateTLoginScreen.invoke()
            }

            is VerificationEmailUiState.Loading -> {
                showLoader.value = true
            }

            is VerificationEmailUiState.Error -> {
                showLoader.value = false
                Toast.makeText(context, currentState.message, Toast.LENGTH_LONG).show()
            }

            else -> {
                showLoader.value = false
            }
        }
    }

    LaunchedEffect(resendEmailVerificationUiState) {
        when (val currentState = resendEmailVerificationUiState) {
            is ResendDVerificationEmailState.Success -> {
                showLoader.value = false
                showCodeMessage.value = true
            }

            is ResendDVerificationEmailState.Loading -> {
                showLoader.value = true
            }

            is ResendDVerificationEmailState.Error -> {
                showLoader.value = false
                Toast.makeText(context, currentState.message, Toast.LENGTH_LONG).show()
            }

            else -> {
                showLoader.value = false
                showCodeMessage.value = false
            }
        }
    }

    VerifyEmailScreenScreenUI(
        context = context,
        paddingValues = paddingValues,
        showLoader = showLoader.value,
        showCodeMessage = showCodeMessage.value,
        formState = formState,
        onEmailFocusLost = viewModel::validateEmailOnFocusLost,
        onEmailChange = viewModel::updateEmail,
        onCodeChange = viewModel::updateCode,
        onSaveClick = viewModel::verifyEmail,
        onResendCode = viewModel::resendCode,
        onToggleCodeMessage = {
            showCodeMessage.value = false
        },
    )
}

@Composable
fun VerifyEmailScreenScreenUI(
    context: Context,
    paddingValues: PaddingValues,
    showLoader: Boolean,
    showCodeMessage: Boolean,
    formState: ConfirmEmailState,
    onEmailFocusLost: () -> Unit,
    onEmailChange: (String) -> Unit,
    onCodeChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onResendCode: () -> Unit,
    onToggleCodeMessage: () -> Unit,
) {

    Box(
        modifier = Modifier
            .background(color = colorResource(R.color.floral_white))
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.lock_large_ic),
                contentDescription = null,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(com.project.feature_auth_module.R.string.verify_your_email),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                        .padding(top = 6.dp, start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomCodeTextField(
                showCodeMessage = showCodeMessage,
                value = formState.code,
                onValueChange = onCodeChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = stringResource(com.project.feature_auth_module.R.string.code),
                onClick = {
                    onToggleCodeMessage()
                }
            )
            if (showCodeMessage) {
                Text(
                    text = context.getString(com.project.feature_auth_module.R.string.request_was_sent_out),
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.salad_green),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, start = 4.dp)
                )
            }

            Text(
                text = stringResource(com.project.feature_auth_module.R.string.resend_code),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    color = colorResource(id = R.color.red),
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .padding(top = 6.dp)
                    .align(Alignment.End)
                    .clickable { onResendCode.invoke() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SubmitButton(
                text = stringResource(com.project.feature_auth_module.R.string.submit),
                isActive = formState.code.length == 4,
                onClick = { onSaveClick.invoke() })

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
