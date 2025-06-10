package com.psfilter.feature_auth_module.ui.presentation.verifyemail

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.booktrails.ui_module.CustomCodeTextField
import com.booktrails.ui_module.CustomInputTextField
import com.booktrails.ui_module.R
import com.booktrails.ui_module.SubmitButton
import com.psfilter.feature_auth_module.ui.presentation.verifyemail.state.ConfirmEmailState
import com.psfilter.feature_auth_module.ui.presentation.verifyemail.viewmodel.VerifyEmailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun VerifyEmailScreen(
    paddingValues: PaddingValues,
    onSaveClick: () -> Unit,
) {

    val viewModel: VerifyEmailViewModel = koinViewModel()
    val formState by viewModel.formState

    VerifyEmailScreenScreenUI(
        paddingValues = paddingValues,
        formState = formState,
        isLoading = false, //TODO
        onSaveClick = onSaveClick,
        onEmailFocusLost = viewModel::validateEmailOnFocusLost,
        onEmailChange = viewModel::updateEmail,
        onCodeChange = viewModel::updateCode,
        onResendCode = viewModel::resendCode
    )
}

@Composable
fun VerifyEmailScreenScreenUI(
    paddingValues: PaddingValues,
    formState: ConfirmEmailState,
    onEmailFocusLost: () -> Unit,
    onEmailChange: (String) -> Unit,
    onCodeChange: (String) -> Unit,
    isLoading: Boolean,
    onSaveClick: () -> Unit,
    onResendCode: () -> Unit
) {

    val buttonText = "Submit" //TODO
    val isButtonActive = true //TODO

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
                value = formState.code,
                onValueChange = onCodeChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = stringResource(com.project.feature_auth_module.R.string.code),
            )

            Text(
                text = stringResource(com.project.feature_auth_module.R.string.resend_code),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    color = colorResource(id = R.color.red)
                ),
                modifier = Modifier
                    .padding(top = 6.dp)
                    .align(Alignment.End)
                    .clickable { onResendCode.invoke() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SubmitButton(buttonText, isButtonActive,{ onSaveClick.invoke() })

        }
    }
    if (isLoading) {
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