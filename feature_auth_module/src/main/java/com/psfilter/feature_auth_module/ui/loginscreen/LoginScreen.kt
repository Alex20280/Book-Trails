package com.project.feature_auth_module.ui

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.booktrails.ui_module.SubmitButton
import com.booktrails.ui_module.R

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
        onFaceBookSignInClick = {},  //TODO
        isLoading = false //TODO
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenUI(
    paddingValues: PaddingValues,
    onClickForgetPassword: () -> Unit,
    onRegisterClick: () -> Unit,
    onSignInClick: () -> Unit,
    onGoogleSignInCLick: () -> Unit,
    onFaceBookSignInClick: () -> Unit,
    isLoading: Boolean
) {

    val rememberMeState = rememberSaveable { mutableStateOf(false) }
    val loginText = rememberSaveable { mutableStateOf("") }
    val passwordText = rememberSaveable { mutableStateOf("") }

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
            Text(
                text = stringResource(R.string.sign_in_title),
                style = MaterialTheme.typography.headlineLarge,
                color = colorResource(id = R.color.black),
                modifier = Modifier.padding(top = 38.dp, start = 16.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = loginText.value,
                onValueChange = { loginText.value = it },
                label = { Text(stringResource(R.string.login)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = colorResource(id = R.color.light_grey),
                    unfocusedLabelColor = colorResource(id = R.color.light_grey),
                    focusedBorderColor = colorResource(id = R.color.light_grey),
                    unfocusedBorderColor = colorResource(id = R.color.light_grey),
                    focusedTextColor = colorResource(id = R.color.black),
                ),
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = passwordText.value,
                onValueChange = { passwordText.value = it },
                label = { Text(stringResource(R.string.password)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = colorResource(id = R.color.light_grey),
                    unfocusedLabelColor = colorResource(id = R.color.light_grey),
                    focusedBorderColor = colorResource(id = R.color.light_grey),
                    unfocusedBorderColor = colorResource(id = R.color.light_grey),
                    focusedTextColor = colorResource(id = R.color.black),
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMeState.value,
                        onCheckedChange = { rememberMeState.value = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = colorResource(id = R.color.blue),
                            uncheckedColor = colorResource(id = R.color.light_grey)
                        )
                    )
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = stringResource(R.string.remember_me)
                    )
                }

                TextButton(onClick = { if (!isLoading) onClickForgetPassword.invoke() }) {
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(id = R.color.blue),
                        text = stringResource(R.string.forget_password),
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            SubmitButton(
                onClick = { if (!isLoading) onSignInClick() },
                text = stringResource(R.string.sign_in)
            )

            Spacer(modifier = Modifier.height(8.dp))

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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.light_grey),
                text = stringResource(R.string.or)
            )

            Spacer(modifier = Modifier.height(16.dp))

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
