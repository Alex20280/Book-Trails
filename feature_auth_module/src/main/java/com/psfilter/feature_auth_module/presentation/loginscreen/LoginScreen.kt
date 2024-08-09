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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.booktrails.core_module.errorhandling.RequestResult
import com.booktrails.ui_module.SubmitButton
import com.booktrails.ui_module.R
import com.psfilter.feature_auth_module.presentation.loginscreen.LoginViewModel
import com.psfilter.feature_auth_module.domain.validationservice.EmailState
import com.psfilter.feature_auth_module.domain.validationservice.PasswordState
import org.koin.androidx.compose.koinViewModel
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.focusable
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester

@Composable
fun LoginScreen(
    paddingValues: PaddingValues,
    onClickForgetPassword: () -> Unit,
    onRegisterClick: () -> Unit,
    onSignInClick: () -> Unit,
) {

    val logInViewModel: LoginViewModel = koinViewModel()
    val errorMessage by logInViewModel.errorMessage.collectAsState()
    val context = LocalContext.current
    val logInResult by logInViewModel.logInResultState.collectAsState()

    val loginText = remember { EmailState() }
    val passwordText = remember { PasswordState() }
    var isLoading by remember { mutableStateOf(false) }
    val loginFocusRequester = remember { FocusRequester() }

    val intentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            logInViewModel.handleActivityResult(result)
        }
    )

    LaunchedEffect(logInResult) {
        when (logInResult) {
            is RequestResult.Success -> {
                onSignInClick.invoke()
                logInViewModel.setLogInResultState(RequestResult.None)
                isLoading = false
            }
            is RequestResult.Loading -> {
                isLoading = true
            }
            else -> {
                isLoading = false
            }
        }
    }

    errorMessage?.let { message ->
        LaunchedEffect(message) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    LoginScreenUI(
        loginFocusRequester= loginFocusRequester,
        loginText = loginText,
        passwordText = passwordText,
        paddingValues = paddingValues,
        onClickForgetPassword = onClickForgetPassword,
        onRegisterClick = onRegisterClick,
        onSignInClick = {logInViewModel.emailPasswordSignIn(loginText.text, passwordText.text)},
        onGoogleSignInCLick = {intentLauncher.launch((logInViewModel.getGoogleSignUpIntent()))},
        onFaceBookSignInClick = {}, // TODO
        isLoading = isLoading
    )
}

@Composable
fun LoginScreenUI(
    loginFocusRequester: FocusRequester,
    loginText: EmailState,
    passwordText: PasswordState,
    paddingValues: PaddingValues,
    onClickForgetPassword: () -> Unit,
    onRegisterClick: () -> Unit,
    onSignInClick: () -> Unit,
    onGoogleSignInCLick: () -> Unit,
    onFaceBookSignInClick: () -> Unit,
    isLoading: Boolean
) {

    val rememberMeState = rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = paddingValues.calculateBottomPadding()
                )
                .alpha(if (isLoading) 0.3f else 1f), // Apply opacity to content
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SignInTitle()
            Spacer(modifier = Modifier.height(24.dp))
            LogInEditText(loginText, loginFocusRequester)
            Spacer(modifier = Modifier.height(16.dp))
            PasswordLoginEditText(passwordText)
            Spacer(modifier = Modifier.height(8.dp))
            ForgetPassword(isLoading, rememberMeState, onClickForgetPassword)
            Spacer(modifier = Modifier.height(24.dp))
            SubmitButton(
                onClick = { if (!isLoading) onSignInClick() },
                text = stringResource(R.string.sign_in),
                enabled =  loginText.isValid() && passwordText.isValid()
            )
            Spacer(modifier = Modifier.height(8.dp))
            DontHaveAccountAndRegister(isLoading, onRegisterClick)
            Spacer(modifier = Modifier.height(8.dp))
            OrText()
            Spacer(modifier = Modifier.height(16.dp))
            GoogleAndFaceBookIcons(isLoading, onGoogleSignInCLick, onFaceBookSignInClick)
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
                    color = colorResource(id = R.color.blue)
                )
            }
        }
    }
}

@Composable
fun OrText() {
    Text(
        style = MaterialTheme.typography.bodyMedium,
        color = colorResource(id = R.color.light_grey),
        text = stringResource(R.string.or)
    )
}

@Composable
fun GoogleAndFaceBookIcons(isLoading: Boolean, onGoogleSignInCLick: () -> Unit, onFaceBookSignInClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            modifier = Modifier.padding(end = 6.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.black),
            text = stringResource(R.string.continue_with_google)
        )

        Image(
            painter = painterResource(id = R.drawable.google_icon),
            contentDescription = stringResource(R.string.google_icon),
            modifier = Modifier
                .size(54.dp)
                .clickable {
                    if (!isLoading) onGoogleSignInCLick.invoke()
                }
        )

        Spacer(modifier = Modifier.width(16.dp))

        Image(
            painter = painterResource(id = R.drawable.fb_icon),
            contentDescription = stringResource(R.string.facebook_icon),
            modifier = Modifier
                .size(42.dp)
                .clickable {
                    if (!isLoading) onFaceBookSignInClick.invoke()
                }
        )
    }
}

@Composable
fun DontHaveAccountAndRegister(isLoading: Boolean, onRegisterClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = stringResource(R.string.don_t_have_an_account)
        )

        TextButton(onClick = { if (!isLoading) onRegisterClick.invoke() }) {
            Text(
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.blue),
                text = stringResource(R.string.register)
            )
        }
    }
}

@Composable
fun ForgetPassword(isLoading: Boolean, rememberMeState: MutableState<Boolean>, onClickForgetPassword: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(onClick = { if (!isLoading) onClickForgetPassword.invoke() }) {
            Text(
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.blue),
                text = stringResource(R.string.forget_password),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordLoginEditText(passwordText: PasswordState) {
    OutlinedTextField(
        value = passwordText.text,
        onValueChange = {
            passwordText.text = it
            if (passwordText.text.isNotEmpty()) {
                passwordText.validate()
            } else {
                passwordText.error = null
            }
        },
        label = { Text(stringResource(R.string.password)) },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedLabelColor = colorResource(id = R.color.light_grey),
            unfocusedLabelColor = colorResource(id = R.color.light_grey),
            focusedBorderColor = colorResource(id = R.color.light_grey),
            unfocusedBorderColor = colorResource(id = R.color.light_grey),
            focusedTextColor = colorResource(id = R.color.black),
        ),
        isError = passwordText.error != null
    )
    passwordText.error?.let { error ->
        ErrorField(error)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInEditText(loginText: EmailState, loginFocusRequester: FocusRequester) {
    Column {
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
            label = { Text(stringResource(R.string.login)) },
            modifier = Modifier.fillMaxWidth().focusable().focusRequester(loginFocusRequester),
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
}

@Composable
fun SignInTitle() {
    Text(
        text = stringResource(R.string.sign_in_title),
        style = MaterialTheme.typography.headlineLarge,
        color = colorResource(id = R.color.black),
        modifier = Modifier.padding(top = 38.dp, start = 16.dp),
    )
}
