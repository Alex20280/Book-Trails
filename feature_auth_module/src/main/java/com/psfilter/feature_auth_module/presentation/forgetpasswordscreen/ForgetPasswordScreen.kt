package com.project.feature_auth_module.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.booktrails.core_module.errorhandling.RequestResult
import com.booktrails.ui_module.R
import com.booktrails.ui_module.SubmitButton
import com.psfilter.feature_auth_module.presentation.forgetpasswordscreen.ForgetPasswordViewModel
import com.psfilter.feature_auth_module.presentation.signupscreen.SignUpViewModel
import com.psfilter.feature_auth_module.domain.validationservice.EmailState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForgetPasswordScreen(
    paddingValues: PaddingValues,
    onClickBackButton: () -> Unit,
    onRestorePasswordClick: () -> Unit,
) {

    val forgetPasswordViewModel: ForgetPasswordViewModel = koinViewModel()
    val errorMessage by forgetPasswordViewModel.errorMessage.collectAsState()
    val context = LocalContext.current
    val resetPasswordResult by forgetPasswordViewModel.resetPasswordResult.collectAsState()

    val loginText = remember { EmailState() }

    LaunchedEffect(resetPasswordResult) {
        if (resetPasswordResult is RequestResult.Success) {
            onRestorePasswordClick.invoke()
            forgetPasswordViewModel.setResetPasswordResult(RequestResult.None)
        }
    }

    errorMessage?.let { message ->
        LaunchedEffect(message) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    ForgetPasswordScreenUI(
        loginText = loginText,
        paddingValues = paddingValues,
        onClickBackButton = onClickBackButton,
        onRestorePasswordClick = {
            forgetPasswordViewModel.resetPassword(loginText.text)
        },
    )
}

@Composable
fun ForgetPasswordScreenUI(
    loginText: EmailState,
    paddingValues: PaddingValues,
    onClickBackButton: () -> Unit,
    onRestorePasswordClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = paddingValues.calculateBottomPadding(),
                top = paddingValues.calculateTopPadding()
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Spacer(modifier = Modifier.height(16.dp))
        ArrowBack(onClickBackButton) //TODO: Bug - when click twice go back to splash screen
        ResetTitleText()
        MainText()
        Spacer(modifier = Modifier.height(24.dp))
        ForgetPassEmailEditText(loginText)
        Spacer(modifier = Modifier.height(8.dp))
        SubmitButton(
            onClick = onRestorePasswordClick,
            text = stringResource(R.string.send_email),
            enabled = loginText.isValid()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPassEmailEditText(loginText: EmailState) {
    OutlinedTextField(
        value = loginText.text,
        onValueChange = {
            loginText.text = it
            if (loginText.text.isNotEmpty()) {
                loginText.validate()
            } else {
                loginText.error = null
            }
        },
        label = { Text(stringResource(R.string.email)) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedLabelColor = colorResource(id = R.color.light_grey),
            unfocusedLabelColor = colorResource(id = R.color.light_grey),
            focusedBorderColor = colorResource(id = R.color.light_grey),
            unfocusedBorderColor = colorResource(id = R.color.light_grey),
            focusedTextColor = colorResource(id = R.color.black),
        ),
        isError = loginText.error != null
    )
    loginText.error?.let { error ->
        ErrorField(error)
    }
}

@Composable
fun MainText() {
    Text(
        modifier = Modifier.padding(top = 16.dp),
        text = stringResource(R.string.type_your_email_and_we_will_send_a_link_to_reset_your_password),
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
fun ResetTitleText() {
    Text(
        modifier = Modifier.padding(top = 38.dp),
        text = stringResource(R.string.reset_password),
        style = MaterialTheme.typography.headlineLarge,
    )
}

@Composable
fun ArrowBack(onClickBackButton: () -> Unit) {
    Image(
        modifier = Modifier
            .clickable { onClickBackButton.invoke() }
            .size(24.dp),
        painter = painterResource(id = R.drawable.arrow_back_icon),
        contentDescription = stringResource(R.string.arrow_back)
    )
}
