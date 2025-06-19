package com.psfilter.feature_auth_module.ui.presentation.createnewpassword

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.booktrails.ui_module.CustomCodeTextField
import com.booktrails.ui_module.CustomPasswordTextField
import com.booktrails.ui_module.R
import com.booktrails.ui_module.SubmitButton
import com.psfilter.feature_auth_module.ui.presentation.createnewpassword.state.SetNewPasswordFormState
import com.psfilter.feature_auth_module.ui.presentation.createnewpassword.state.SetNewPassPasswordUiState
import com.psfilter.feature_auth_module.ui.presentation.createnewpassword.viewmodel.SetNewPasswordViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateNewPasswordScreen(
    paddingValues: PaddingValues,
    email: String,
    verificationCode: String,
    navigateToLoginScreen: () -> Unit
) {

    val context = LocalContext.current
    val showLoader = remember { mutableStateOf(false) }
    val viewModel: SetNewPasswordViewModel = koinViewModel()
    val formState by viewModel.formState
    val forgetPasswordState by viewModel.forgetPasswordState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setInitialEmailAndCode(email, verificationCode)
    }

    LaunchedEffect(forgetPasswordState) {
        when (val currentState = forgetPasswordState) {
            is SetNewPassPasswordUiState.Success -> {
                showLoader.value = false
                navigateToLoginScreen.invoke()
            }

            is SetNewPassPasswordUiState.Loading -> {
                showLoader.value = true
            }

            is SetNewPassPasswordUiState.Error -> {
                showLoader.value = false
                Toast.makeText(context, currentState.message, Toast.LENGTH_LONG).show()
            }

            else -> {
                showLoader.value = false
            }
        }
    }

    CreateNewPasswordScreenUI(
        paddingValues = paddingValues,
        showLoader = showLoader.value,
        onSaveClick = { viewModel.setNewPassword() },
        formState = formState,
        isButtonEnabled = viewModel.isSaveButtonEnabled(),
        onPasswordChange = viewModel::updatePassword,
        onCodeChange = viewModel::updateCode,
        onConfirmPasswordChange = viewModel::updateConfirmPassword,
        onPasswordFocusLost = viewModel::validatePasswordOnFocusLost,
        onConfirmPasswordFocusLost = viewModel::validateConfirmPasswordOnFocusLost,
        onCodeFocusLost = viewModel::validateCodeOnFocusLost,
    )
}

@Composable
fun CreateNewPasswordScreenUI(
    paddingValues: PaddingValues,
    showLoader: Boolean,
    onSaveClick: () -> Unit,
    formState: SetNewPasswordFormState,
    isButtonEnabled: Boolean,
    onPasswordChange: (String) -> Unit,
    onCodeChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onPasswordFocusLost: () -> Unit,
    onConfirmPasswordFocusLost: () -> Unit,
    onCodeFocusLost: () -> Unit,
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
            painter = painterResource(id = R.drawable.lock_large_ic),
            contentDescription = null,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = stringResource(com.project.feature_auth_module.R.string.create_new_password),
            style = MaterialTheme.typography.headlineLarge,
            fontFamily = FontFamily(Font(R.font.roboto_bold)),
            fontSize = 26.sp,
            color = colorResource(id = R.color.dark_brown),
            modifier = Modifier.padding(top = 15.dp, start = 12.dp),
        )

        Spacer(modifier = Modifier.height(12.dp))

        CustomCodeTextField(
            isError = formState.verificationCodeError != null,
            value = formState.verificationCode.raw,
            onValueChange = onCodeChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = stringResource(com.project.feature_auth_module.R.string.code),
            onFocusChanged = { hasFocus ->
                if (!hasFocus) onCodeFocusLost()
            }
        )
        formState.verificationCodeError?.let { error ->
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
            value = formState.password.raw,
            onValueChange = { value ->
                onPasswordChange(value.trimEnd())
            },
            onFocusChanged = { hasFocus ->
                if (!hasFocus) onPasswordFocusLost()
            },
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

        Spacer(modifier = Modifier.height(10.dp))

        CustomPasswordTextField(
            value = formState.confirmPassword.raw,
            onValueChange = { value ->
                onConfirmPasswordChange(value.trimEnd())
            },
            onFocusChanged = { hasFocus ->
                if (!hasFocus) onConfirmPasswordFocusLost()
            },
            modifier = Modifier.fillMaxWidth(),
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

        SubmitButton(stringResource(com.project.feature_auth_module.R.string.save), isButtonEnabled, { onSaveClick.invoke() })
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