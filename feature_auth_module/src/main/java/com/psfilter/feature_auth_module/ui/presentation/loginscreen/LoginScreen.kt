package com.project.feature_auth_module.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.booktrails.ui_module.CustomPasswordTextField
import com.booktrails.ui_module.CustomUserNameTextField
import com.booktrails.ui_module.R
import com.booktrails.ui_module.SubmitButton
import com.psfilter.feature_auth_module.ui.presentation.Login
import com.psfilter.feature_auth_module.ui.presentation.Password

@Composable
fun LoginScreen(
    paddingValues: PaddingValues,
    onClickForgetPassword: () -> Unit,
    onRegisterClick: () -> Unit,
    onSignInClick: () -> Unit,
) {

    LoginScreenUI(
        paddingValues = paddingValues,
        onClickForgetPassword = onClickForgetPassword,
        onRegisterClick = onRegisterClick,
        onSignInClick = onSignInClick,
        onGoogleSignInCLick = {}, //TODO
        isLoading = false //TODO
    )
}

@Composable
fun LoginScreenUI(
    paddingValues: PaddingValues,
    isLoading: Boolean,
    onClickForgetPassword: () -> Unit,
    onRegisterClick: () -> Unit,
    onSignInClick: () -> Unit,
    onGoogleSignInCLick: () -> Unit,
) {

/*    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()*/

    val loginText = remember { mutableStateOf(Login("")) }
    val loginPlaceholder =  stringResource(R.string.login)
    val loginErrorMessage = "Login is invalid" //TODO
    val isLoginInError = false //TODO

    val passwordText = remember { mutableStateOf(Password("")) }
    val passwordPlaceholder =  stringResource(R.string.password)
    val passwordErrorMessage = "Enter Password" //TODO
    val isPasswordInError = false //TODO

    val buttonText = "Submit" //TODO
    val isButtonActive = true //TODO

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

        CustomUserNameTextField(
            value = loginText.value.raw,
            onValueChange = { loginText.value = Login(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = loginPlaceholder,
            errorMessage = loginErrorMessage,
            isError = isLoginInError
        )

        Spacer(modifier = Modifier.height(14.dp))

        CustomPasswordTextField(
            value = passwordText.value.raw,
            onValueChange = { passwordText.value = Password(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = passwordPlaceholder,
            errorMessage = passwordErrorMessage,
            isError = isPasswordInError
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            TextButton(onClick = { if (!isLoading) onClickForgetPassword.invoke() }) {
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.antique_rose),
                    text = stringResource(R.string.forget_password),
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        SubmitButton(buttonText, isButtonActive, {onSignInClick.invoke()}, )

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

            TextButton(onClick = { if (!isLoading) onRegisterClick.invoke() }) {
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
                        if (!isLoading) onGoogleSignInCLick.invoke()
                    }
            )

            Spacer(modifier = Modifier.width(16.dp))

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
}