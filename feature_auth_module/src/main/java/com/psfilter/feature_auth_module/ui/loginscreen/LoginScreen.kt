package com.project.feature_auth_module.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.feature_auth_module.R

@Composable
fun LoginScreen(
    paddingValues: PaddingValues,
    onClickForgetPassword: () -> Unit,
    onRegisterClick: () -> Unit,
    onSignInClick: () -> Unit,
) {

    /*    val viewModel = hiltViewModel<DetailsViewModel>()
        val screenDetails = viewModel.feedDetails.collectAsState()*/

    LoginScreenUI(
        paddingValues = paddingValues,
        onClickForgetPassword = onClickForgetPassword,
        onRegisterClick = onRegisterClick,
        onSignInClick = onSignInClick,
        /*        screenDetails = screenDetails,
                onTextClicked = {navigate(screenDetails.value.link?.toUri()?.toString() ?: "")},
                onIconClicked = {viewModel.toggleBookmark(it)}*/
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenUI(
    paddingValues: PaddingValues,
    onClickForgetPassword: () -> Unit,
    onRegisterClick: () -> Unit,
    onSignInClick: () -> Unit,
) {

    val rememberMeState = remember { mutableStateOf(false) }
    val loginText = remember { mutableStateOf("") }
    val passwordText = remember { mutableStateOf("") }

    /*    Text(
            text = stringResource(R.string.sign_in_title),
            fontFamily = FontFamily(Font(com.booktrails.ui_module.R.font.roboto_bold)),
            fontWeight = FontWeight.W500,
            fontSize = 25.sp,
            color = colorResource(id = com.booktrails.ui_module.R.color.black),
            modifier = Modifier.padding(top = 38.dp, start = 16.dp),
        )*/

    Text(
        text = stringResource(R.string.sign_in_title),
        style = MaterialTheme.typography.headlineLarge,
        color = colorResource(id = com.booktrails.ui_module.R.color.black),
        modifier = Modifier.padding(top = 38.dp, start = 16.dp),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = paddingValues.calculateBottomPadding()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = loginText.value,
            onValueChange = { loginText.value = it },
            label = { Text(stringResource(R.string.login)) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                unfocusedLabelColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                focusedBorderColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                unfocusedBorderColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                focusedTextColor = colorResource(id = com.booktrails.ui_module.R.color.black),
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
                focusedLabelColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                unfocusedLabelColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                focusedBorderColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                unfocusedBorderColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
                focusedTextColor = colorResource(id = com.booktrails.ui_module.R.color.black),
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
                        checkedColor = colorResource(id = com.booktrails.ui_module.R.color.blue),
                        uncheckedColor = colorResource(id = com.booktrails.ui_module.R.color.light_grey)
                    )
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(R.string.remember_me)
                )
            }

            TextButton(onClick = { /* Handle forget password */ }) {
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.clickable {
                        onClickForgetPassword.invoke()
                    },
                    color = (colorResource(id = com.booktrails.ui_module.R.color.blue)),
                    text = stringResource(R.string.forget_password),
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onSignInClick.invoke() },
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = com.booktrails.ui_module.R.color.blue),
                contentColor = colorResource(id = com.booktrails.ui_module.R.color.white),

                ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                text = stringResource(R.string.sign_in)
            )
        }

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

            TextButton(onClick = { onRegisterClick.invoke() }) {
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    color = (colorResource(id = com.booktrails.ui_module.R.color.blue)),
                    text = stringResource(R.string.register)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = com.booktrails.ui_module.R.color.light_grey),
            text = stringResource(R.string.or)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle Google sign in */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = com.booktrails.ui_module.R.color.very_light_grey),
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.google_icon),
                    contentDescription = stringResource(R.string.google_icon),
                    modifier = Modifier.size(34.dp) // Adjust the size as needed
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = com.booktrails.ui_module.R.color.black),
                    text = stringResource(R.string.continue_with_google)
                )
            }
        }

    }
}